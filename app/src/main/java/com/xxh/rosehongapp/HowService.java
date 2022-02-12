package com.xxh.rosehongapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

public class HowService extends Service {

    private IHowBinder iHowBinder = new IHowBinder.Stub() {
        @Override
        public int callRemote(int value) throws RemoteException {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HowService.this, "this is binder service", Toast.LENGTH_SHORT).show();
                }
            });
            return 0;
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public HowService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iHowBinder.asBinder();
    }
}