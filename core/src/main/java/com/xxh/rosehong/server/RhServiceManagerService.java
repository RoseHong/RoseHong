package com.xxh.rosehong.server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import com.xxh.rosehong.bridge.IRhServiceManager;
import com.xxh.rosehong.config.RhSystemConfig;
import com.xxh.rosehong.utils.system.RhLog;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */

/**
 * 服务进程的入口且管理着所有的自定义Service binder
 */
public class RhServiceManagerService extends ContentProvider {
    private static final String TAG = RhServiceManagerService.class.getSimpleName();

    private Map<String, IBinder> mServiceCache = new HashMap<>();
    private RhServiceManagerInternal mInternal = new RhServiceManagerInternal();
    private static boolean mIsInit = false;

    private void init() {
        if (mIsInit) {
            return;
        }
        mIsInit = true;
    }

    private RhServiceManagerInternal get() {
        return mInternal;
    }

    @Override
    public boolean onCreate() {
        init();
        return true;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (!TextUtils.isEmpty(method)) {
            if (method.equals(RhSystemConfig.SERVER_INIT_METHOD)) {
                Bundle resBundle = new Bundle();
                /**
                 * 直接传递mInternal，方便客户端/服务器完成相关的register、get和unregister操作
                 */
                resBundle.putBinder(RhSystemConfig.KEY_SERVER_CONTROL_BINDER, get());
                return resBundle;
            }
        }
        return super.call(method, arg, extras);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    //使用内部类的好处，可以直接操作mServiceCache
    private class RhServiceManagerInternal extends IRhServiceManager.Stub {

        @Override
        public IBinder getService(String serviceName) {
            return mServiceCache.get(serviceName);
        }

        @Override
        public void register(String serviceName, IBinder service) {
            if (mServiceCache.containsKey(serviceName)) {
                RhLog.w(TAG, "Repeat registration! serviceName: " + serviceName);
                return;
            }
            mServiceCache.put(serviceName, service);
        }

        @Override
        public IBinder unregister(String serviceName) {
            return mServiceCache.remove(serviceName);
        }
    }
}
