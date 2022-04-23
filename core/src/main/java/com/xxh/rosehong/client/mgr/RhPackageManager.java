package com.xxh.rosehong.client.mgr;

import android.os.RemoteException;

import com.xxh.rosehong.bridge.IRhPackageManager;
import com.xxh.rosehong.config.RhSystemConfig;
import com.xxh.rosehong.model.RhInstallResMod;

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
}
