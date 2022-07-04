package com.xxh.rosehong.utils.system;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhProviderHelper {
    private static final String TAG = RhProviderHelper.class.getSimpleName();

    public static Bundle call(Context context, String authority, String method, String arg, Bundle extras) {
        return call(context, authority, method, arg, extras, 5, 100);
    }

    public static Bundle call(Context context, String authority, String method, String arg, Bundle extras, int retryCount, int timeOutMs) {
        Uri uri = Uri.parse("content://" + authority);
        ContentResolver contentResolver = context.getContentResolver();
        ContentProviderClient contentProviderClient = contentResolver.acquireContentProviderClient(uri);
        while (--retryCount >= 0 && contentResolver == null) {
            SystemClock.sleep(timeOutMs);
            contentProviderClient = contentResolver.acquireContentProviderClient(uri);
        }
        if (contentProviderClient == null) {
            return null;
        }
        Bundle resBundle = null;
        try {
            resBundle = contentProviderClient.call(method, arg, extras);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            contentProviderClient.close();
        }
        return resBundle;
    }
}
