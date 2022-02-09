package com.xxh.rosehong.utils.system;

import android.util.Log;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/3 上午12:17
 */
public class RhLog {
    private static final String TAG = RhLog.class.getSimpleName();

    public static boolean mIsLoggable = false;

    public static void openLog() {
        mIsLoggable = true;
    }

    public static void v(String tag, String format, Object... args) {
        if (mIsLoggable) {
            v(tag, String.format(format, args));
        }
    }

    public static void v(String tag, String msg) {
        if (mIsLoggable) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String format, Object... args) {
        if (mIsLoggable) {
            w(tag, String.format(format, args));
        }
    }

    public static void w(String tag, String msg) {
        if (mIsLoggable) {
            Log.w(tag, msg);
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (mIsLoggable) {
            i(tag, String.format(format, args));
        }
    }

    public static void i(String tag, String msg) {
        if (mIsLoggable) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String format, Object... args) {
        if (mIsLoggable) {
            d(tag, String.format(format, args));
        }
    }

    public static void d(String tag, String msg) {
        if (mIsLoggable) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (mIsLoggable) {
            e(tag, String.format(format, args));
        }
    }

    public static void e(String tag, String msg) {
        if (mIsLoggable) {
            Log.e(tag, msg);
        }
    }

    public static void printStackTrace() {
        if (mIsLoggable) {
            printStackTrace(TAG);
        }
    }
    public static void printStackTrace(String tag) {
        if (mIsLoggable) {
            printStackTrace(tag, new Exception());
        }
    }
    public static void printStackTrace(String tag, Throwable e) {
        if (mIsLoggable) {
            Log.e(tag, Log.getStackTraceString(e));
        }
    }

    public static void throwException() throws Exception {
        throwException(new Exception());
    }
    public static void throwException(Exception e) throws Exception {
        throw e;
    }
}
