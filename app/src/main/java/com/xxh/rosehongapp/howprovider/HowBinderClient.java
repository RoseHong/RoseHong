package com.xxh.rosehongapp.howprovider;

import android.os.IBinder;
import android.os.RemoteException;

import com.xxh.rosehongapp.IHowBinder;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/20 下午4:47
 */
public class HowBinderClient {
    private IBinder service;
    private static HowBinderClient sInstance = new HowBinderClient();

    public static HowBinderClient getsInstance() {
        return sInstance;
    }

    private IHowBinder getService() {
        if (service == null) {
            service = HowProviderClient.getInstance().getService("howbinder");
        }
        return IHowBinder.Stub.asInterface(service);
    }

    public int callRemote(int value) {
        try {
            return getService().callRemote(12);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
