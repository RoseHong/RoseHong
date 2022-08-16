package com.xxh.rosehong.server.pm;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.xxh.rosehong.R;
import com.xxh.rosehong.config.RhCustomConfig;
import com.xxh.rosehong.config.RhSystemConfig;
import com.xxh.rosehong.framework.ref.android.internal.content.NativeLibraryHelperRef;
import com.xxh.rosehong.model.RhInstallResMod;
import com.xxh.rosehong.server.pm.parser.RhPackage;
import com.xxh.rosehong.server.pm.parser.RhPackageParser;
import com.xxh.rosehong.server.refit.RhApplicationInfoRefit;
import com.xxh.rosehong.utils.system.RhBuild;
import com.xxh.rosehong.utils.system.RhFile;
import com.xxh.rosehong.utils.system.zip.RhZipUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/27 23:37
 */
public class RhPackageInstaller {
    private static final String TAG = RhPackageInstaller.class.getSimpleName();

    private Context mContext;

    public RhPackageInstaller(Context context) {
        mContext = context;
    }

    public RhInstallResMod installPackage(String packageFilePath) {
        File packageFile = prepareInstallFiles(packageFilePath);
        if (packageFile == null) {
            return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_package_broken));
        }
        RhPackage rhPackage;
        try {
            rhPackage = RhPackageParser.parsePackage(packageFile);
        } catch (Throwable e) {
            return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_package_parser));
        }
        File innerAppDir = RhCustomConfig.Helper.ensureInnerApkPackageDir(rhPackage.packageName);
        if (innerAppDir == null) {
            return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_folder_create));
        }

        //将apk文件全部拷贝到指定目录
        File baseApk = RhCustomConfig.Helper.ensureInnerApkBaseFile(rhPackage.packageName);
        if (baseApk == null) {
            return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_file_base_create));
        }
        try {
            RhFile.copyFile(new File(rhPackage.baseCodePath), baseApk);
            rhPackage.baseCodePath = baseApk.getAbsolutePath();
        } catch (IOException e) {
            RhFile.deleteDeep(RhCustomConfig.Helper.ensureInnerApkPackageDir(rhPackage.packageName));
            return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_file_base_copy));
        }
        if (rhPackage.isSplitApk()) {
            //split apk也要都拷贝
            for (int i=0; i<rhPackage.splitNames.length; i++) {
                String splitName = rhPackage.splitNames[i];
                File splitApk = RhCustomConfig.Helper.ensureInnerApkSplitFile(rhPackage.packageName, splitName);
                if (splitApk == null) {
                    RhFile.deleteDeep(RhCustomConfig.Helper.ensureInnerApkPackageDir(rhPackage.packageName));
                    return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_file_base_create));
                }
                try {
                    RhFile.copyFile(new File(rhPackage.splitCodePaths[i]), splitApk);
                    rhPackage.splitCodePaths[i] = splitApk.getAbsolutePath();
                } catch (Exception e) {
                    RhFile.deleteDeep(RhCustomConfig.Helper.ensureInnerApkPackageDir(rhPackage.packageName));
                    return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_file_split_copy));
                }
            }
        }

        //将所需.so文件拷贝到lib文件夹
        Object handle = NativeLibraryHelperRef.HandleRef.create.call(packageFile);
        NativeLibraryHelperRef.HandleRef.extractNativeLibs.set(handle, true);
        String packageSureCpuAbi = getPackageSureCupAbi(handle, rhPackage.use32bitAbi);
        if (TextUtils.isEmpty(packageSureCpuAbi)) {
            RhFile.deleteDeep(RhCustomConfig.Helper.ensureInnerApkPackageDir(rhPackage.packageName));
            return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_cup_abi_unsupported));
        }
        File libPath = RhCustomConfig.Helper.ensureInnerApkLibDir(rhPackage.packageName);
        if (libPath == null) {
            RhFile.deleteDeep(RhCustomConfig.Helper.ensureInnerApkPackageDir(rhPackage.packageName));
            return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_file_lib_create));
        }
        NativeLibraryHelperRef.copyNativeBinaries.call(handle, libPath, packageSureCpuAbi);

        // 对applicationInfo进行修正
        rhPackage.applicationInfo = RhApplicationInfoRefit.Builder.create()
                .setApplicationInfo(rhPackage.applicationInfo)
                .setUseLibraries(rhPackage.usesLibraries, rhPackage.usesOptionalLibraries)
                .setCpuAbi(packageSureCpuAbi)
                .setSplitApk(rhPackage.isSplitApk())
                .setSplitCodePaths(rhPackage.splitCodePaths)
                .doRefit();

        return RhInstallResMod.success();
    }

    private String getPackageSureCupAbi(Object handle, boolean use32bitAbi) {
        //如果主进程不支持32位的话，那就返回空，让其安装失败
        if (use32bitAbi && !RhBuild.isAbi32(RhSystemConfig.MAIN_CPU_ABI)) {
            return "";
        }
        if (use32bitAbi) {
            int idx = NativeLibraryHelperRef.findSupportedAbi.call(handle, Build.SUPPORTED_32_BIT_ABIS);
            if (idx >= 0) {
                return Build.SUPPORTED_32_BIT_ABIS[idx];
            }
        } else {
            if (RhBuild.isAbi32(RhSystemConfig.MAIN_CPU_ABI)) {
                int idx = NativeLibraryHelperRef.findSupportedAbi.call(handle, Build.SUPPORTED_32_BIT_ABIS);
                if (idx >= 0) {
                    return Build.SUPPORTED_32_BIT_ABIS[idx];
                }
            } else {
                int idx = NativeLibraryHelperRef.findSupportedAbi.call(handle, Build.SUPPORTED_64_BIT_ABIS);
                if (idx >= 0) {
                    return Build.SUPPORTED_64_BIT_ABIS[idx];
                }
            }
        }

        //这种情况一般是32和64都支持，所以返回跟主进程一样就可以了
        return RhSystemConfig.MAIN_CPU_ABI;
    }

    private String getResourceString(int res) {
        if (mContext == null) {
            return "";
        }

        return mContext.getResources().getString(res);
    }

    /**
     * 准备好所有的安装文件并且返回File对象，若是xapk/apks，则在此方法进行解压且返回路径
     */
    private File prepareInstallFiles(String packageFilePath) {
        File file = new File(packageFilePath);
        if (file.isFile()) {
            if (RhFile.isApkFile(file.getName())) {
                return file;
            } else if (RhFile.isApksFile(file.getName())) {
                //将文件解压出来，并返回目录路径
                String outDirName = RhFile.getFileNameFromPath(packageFilePath).replaceAll(RhFile.APKS, "").replaceAll(RhFile.XAPK, "");
                File fixedFile = RhZipUtil.extractZipFile(file, new File(mContext.getCacheDir(), outDirName));
                if (fixedFile.isDirectory()) {
                    //清掉与安装无关的文件
                    return clearInstallDir(fixedFile);
                }
            }
        } else {
            //清掉与安装无关的文件
            return clearInstallDir(file);
        }
        return null;
    }

    private File clearInstallDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return dir;
        }
        for (File subFile : dir.listFiles()) {
            if (subFile.isDirectory()) {
                RhFile.deleteDeep(subFile);
            } else {
                if (!RhFile.isApkFile(subFile)) {
                    RhFile.deleteDeep(subFile);
                }
            }
        }
        if (dir.listFiles().length == 1) {
            return dir.listFiles()[0];
        }
        return dir;
    }
}
