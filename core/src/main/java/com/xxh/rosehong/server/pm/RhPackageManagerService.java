package com.xxh.rosehong.server.pm;

import com.xxh.rosehong.bridge.IRhPackageManager;
import com.xxh.rosehong.client.RhApp;
import com.xxh.rosehong.model.RhInstallResMod;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhPackageManagerService extends IRhPackageManager.Stub {
    private static final String TAG = RhPackageManagerService.class.getSimpleName();
    private RhPackageInstaller apkInstaller = null;

    @Override
    public RhInstallResMod installApk(String apkPath) {
        if (apkInstaller == null) {
            apkInstaller = new RhPackageInstaller(RhApp.get().getHostContext());
        }
        return apkInstaller.installPackage(apkPath);
    }
}
