package com.xxh.rosehongapp.howprovider;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import com.xxh.rosehong.utils.system.RhLog;
import com.xxh.rosehongapp.IHowBinder;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/20 下午4:32
 */
public class HowBinderServer extends IHowBinder.Stub {
    private static final String TAG = HowBinderServer.class.getSimpleName();
    @Override
    public int callRemote(int value) throws RemoteException {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                RhLog.e(TAG, "this is HowBinderServer callRemote: " + value);
            }
        });
        return 1024;
    }
}
