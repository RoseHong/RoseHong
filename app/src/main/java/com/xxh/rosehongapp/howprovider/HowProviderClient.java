package com.xxh.rosehongapp.howprovider;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.xxh.rosehongapp.IHowBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/20 下午4:45
 */
public class HowProviderClient {
    private static HowProviderClient sInstance = new HowProviderClient();
    private Map<String, IBinder> serviceCache = new HashMap<>();
    private boolean isInited = false;

    public static HowProviderClient getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        if (isInited) return;
        isInited = true;
        ContentResolver contentResolver = context.getContentResolver();
        ContentProviderClient client = contentResolver.acquireContentProviderClient(Uri.parse("content://com.xxh.rosehongapp.howprovider.HowBinderServer"));
        try {
            Bundle res = client.call("@", "", null);
            IBinder binder = res.getBinder("howbinder");
            serviceCache.put("howbinder", binder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public IBinder getService(String serviceName) {
        IBinder binder = serviceCache.get("howbinder");
        if (binder == null) {
            //i don't know
        }
        return binder;
    }
}
