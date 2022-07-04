package com.xxh.rosehong.client.mgr;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.xxh.rosehong.bridge.IRhServiceManager;
import com.xxh.rosehong.client.RhApp;
import com.xxh.rosehong.config.RhCustomConfig;
import com.xxh.rosehong.config.RhSystemConfig;
import com.xxh.rosehong.utils.system.RhLog;
import com.xxh.rosehong.utils.system.RhProviderHelper;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhServiceManager {
    private static final String TAG = RhServiceManager.class.getSimpleName();

    private static IRhServiceManager sServiceManager;

    private static IRhServiceManager getIServiceManager() {
        if (sServiceManager != null) {
            return sServiceManager;
        }
        Bundle resBundle = RhProviderHelper.call(RhApp.get().getHostContext(), RhCustomConfig.SERVER_AUTHOR_NAME, RhSystemConfig.SERVER_INIT_METHOD, "", new Bundle());
        if (resBundle == null) {
            RhLog.printStackTrace(TAG, new RuntimeException("Can not init core server"));
            return null;
        }
        IBinder service = resBundle.getBinder(RhSystemConfig.KEY_SERVER_CONTROL_BINDER);
        if (service == null) {
            return null;
        }
        IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
            @Override
            public void binderDied() {
                service.unlinkToDeath(this, 0);
                sServiceManager = null;
            }
        };
        try {
            service.linkToDeath(deathRecipient, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        sServiceManager = IRhServiceManager.Stub.asInterface(service);
        return sServiceManager;
    }

    public static IBinder getServiceBinder(String serviceName) {
        try {
            return getIServiceManager().getService(serviceName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
