package com.xxh.rosehong.server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import com.xxh.rosehong.config.RhConfig;
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

    private static boolean mIsInit = false;

    private void init() {
        if (mIsInit) {
            return;
        }
        mIsInit = true;


    }

    @Override
    public boolean onCreate() {
        init();
        return true;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (!TextUtils.isEmpty(method)) {
            if (method.equals(RhConfig.SERVER_INIT_METHOD)) {
                
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

    private static class RhServiceCache {
        private Map<String, IBinder> mCache = new HashMap<>();

        public void register(String serviceName, IBinder service) {
            if (mCache.containsKey(serviceName)) {
                RhLog.w(TAG, "Repeat registration! serviceName: " + serviceName);
                return;
            }
            mCache.put(serviceName, service);
        }

        public IBinder getService(String serviceName) {
            return mCache.get(serviceName);
        }

        public IBinder unregister(String serviceName) {
            return mCache.remove(serviceName);
        }
    }
}
