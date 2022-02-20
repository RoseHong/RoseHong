package com.xxh.rosehongapp.howprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/20 下午4:26
 */
public class HowProviderServer extends ContentProvider {

    private Map<String, IBinder> serviceCache = new HashMap<>();

    @Override
    public boolean onCreate() {
        serviceCache.put("howbinder", new HowBinderServer());
        return true;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if ("@".equals(method)) {
            Bundle res = new Bundle();
            res.putBinder("howbinder", serviceCache.get("howbinder"));
            return res;
        }
        return null;
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
}
