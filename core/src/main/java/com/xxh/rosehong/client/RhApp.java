package com.xxh.rosehong.client;

import android.content.Context;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhApp {
    private static final String TAG = RhApp.class.getSimpleName();

    private static RhApp sApp = new RhApp();

    /*未被重新创建的，可以理解为宿主的context*/
    private Context mHostContext = null;

    /*虚拟app的context*/
    private Context mAppContext = null;
    
    public static RhApp get() {
        return sApp;
    }

    public void attachContext(Context context) {
        mHostContext = context;
    }

    public Context getAppContext() {
        return mAppContext;
    }

    public Context getHostContext() {
        return mHostContext;
    }
}
