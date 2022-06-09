package com.xxh.rosehong.server.pm;

import com.xxh.rosehong.model.RhInstallResMod;
import com.xxh.rosehong.utils.system.RhFile;

import java.io.File;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/27 23:37
 */
public class RhApkInstaller {
    private static final String TAG = RhApkInstaller.class.getSimpleName();

    public RhInstallResMod installApk(String apkPath) {
        return RhInstallResMod.success();
    }

    /**
     * 准备好所有的安装文件并且返回File对象，若是xapk/apks，则在此方法进行解压且返回路径
     */
    private File prepareInstallFiles(String apkPath) {
        File file = new File(apkPath);
        if (file.isFile()) {
            if (RhFile.isApkFile(file.getName())) {
                return file;
            } else if (RhFile.isApksFile(file.getName())) {
                //将文件解压出来，并返回目录路径
            }
        }
        //TODO: 清理一下文件夹，避免有其他不必要的文件存在导致解析失败
        return null;
    }

    private File extractApksFile(File apksFile) {
        
    }
}
