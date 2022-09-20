package com.xxh.rosehong.client.mgr;

import android.os.RemoteException;

import com.xxh.rosehong.bridge.IRhPackageManager;
import com.xxh.rosehong.client.RhApp;
import com.xxh.rosehong.config.RhSystemConfig;
import com.xxh.rosehong.model.RhInstallResMod;
import com.xxh.rosehong.utils.system.RhFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhPackageManager extends RhManagerBase<IRhPackageManager> {
    private static final String TAG = RhPackageManager.class.getSimpleName();

    private static RhPackageManager sInstance = new RhPackageManager();

    public RhPackageManager() {
        super(IRhPackageManager.class, RhSystemConfig.RhServiceManagerName.PACKAGE);
    }

    public static RhPackageManager get() {
        return sInstance;
    }

    public RhInstallResMod installApk(String apkPath) {
        try {
            return getService().installApk(apkPath);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new RhInstallResMod(-1, "failed");
    }

    public RhInstallResMod installApkFromAsset(String apkFile) {
        InputStream inputStream = null;
        try {
            inputStream = RhApp.get().getHostContext().getAssets().open(apkFile);
            return installApkFromStream(inputStream, RhFile.getFileExtension(apkFile));
        } catch (Throwable e) {
            e.printStackTrace();
            return RhInstallResMod.failed("failed");
        } finally {
            RhFile.closeQuietly(inputStream);
        }
    }

    public RhInstallResMod installApkFromStream(InputStream inputStream, String extension) {
        File cacheApkFile = RhFile.createCacheFile(RhApp.get().getHostContext(), extension);
        if (cacheApkFile == null) {
            return RhInstallResMod.failed("failed! cache create failed");
        }
        try {
            RhFile.copyStream(inputStream, new FileOutputStream(cacheApkFile));
            return installApk(cacheApkFile.getAbsolutePath());
        } catch (Throwable e) {
            e.printStackTrace();
            return RhInstallResMod.failed("failed!");
        } finally {
            if (cacheApkFile.exists()) {
                RhFile.deleteDeep(cacheApkFile);
            }
        }
    }
}
