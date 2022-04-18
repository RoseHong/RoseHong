package com.xxh.rosehong.client.mgr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhMethodParams;
import com.xxh.rosehong.utils.ref.RhStaticMethod;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhManagerBase<T> {
    private static final String TAG = RhManagerBase.class.getSimpleName();

    private String mBaseServiceName;
    private T mService;
    private static class Stub {
        @RhMethodParams({IBinder.class})
        public static RhStaticMethod<IInterface> asInterface;
    }

    public RhManagerBase(Class iInterface, String baseServiceName) {
        mBaseServiceName = baseServiceName;
        try {
            RhClass.init(Stub.class, iInterface.getName() + "$Stub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected T getService() {
        if (mService == null) {
            IBinder serviceBinder = RhServiceManager.getServiceBinder(mBaseServiceName);
            if (serviceBinder != null) {
                IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        serviceBinder.unlinkToDeath(this, 0);
                        mService = null;
                    }
                };
                try {
                    serviceBinder.linkToDeath(deathRecipient, 0);
                    mService = (T) Stub.asInterface.call(serviceBinder);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return mService;
    }
}
