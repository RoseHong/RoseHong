package com.xxh.rosehong.server.pm;

import com.xxh.rosehong.bridge.IRhPackageManager;
import com.xxh.rosehong.config.RhCustomConfig;
import com.xxh.rosehong.model.RhInstallResMod;
import com.xxh.rosehong.utils.system.RhFile;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhPackageManagerService extends IRhPackageManager.Stub {
    private static final String TAG = RhPackageManagerService.class.getSimpleName();

    @Override
    public RhInstallResMod installApk(String apkPath) {
        //TODO: 解析apk包
        RhFile.copyFile(apkPath, RhCustomConfig.getInnerApkPathByPackageName(""));
        return null;
    }
}
