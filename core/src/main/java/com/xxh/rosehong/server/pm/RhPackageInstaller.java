package com.xxh.rosehong.server.pm;

import android.content.Context;

import com.xxh.rosehong.R;
import com.xxh.rosehong.model.RhInstallResMod;
import com.xxh.rosehong.server.pm.parser.RhPackage;
import com.xxh.rosehong.server.pm.parser.RhPackageParser;
import com.xxh.rosehong.utils.system.RhFile;
import com.xxh.rosehong.utils.system.zip.RhZipUtil;

import java.io.File;

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
        RhPackage rhPackage = null;
        try {
            rhPackage = RhPackageParser.parsePackage(packageFile);
        } catch (Throwable e) {
            return RhInstallResMod.failed(getResourceString(R.string.core_installer_fail_package_parser));
        }
        return RhInstallResMod.success();
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
