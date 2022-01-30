package com.xxh.rosehong.utils.system;

import android.os.Build;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/30 上午12:53
 */
public class RhBuild {
    private static final String TAG = RhBuild.class.getSimpleName();

    public static class VERSION_CODE {
        public static final int N = 25;
        public static final int O = 26;
        public static final int P = 27;
        public static final int Q = 28;
        public static final int R = 29;
        public static final int S = 30;
        public static final int SL = 31;
    }

    public static int getPreviewSDKInt() {
        return Build.VERSION.SDK_INT >= 23 ? Build.VERSION.PREVIEW_SDK_INT : 0;
    }

    public static boolean isO() {
        return Build.VERSION.SDK_INT > VERSION_CODE.N ||
                Build.VERSION.SDK_INT == VERSION_CODE.N && getPreviewSDKInt() > 0;
    }

    public static boolean isP() {
        return Build.VERSION.SDK_INT > VERSION_CODE.O ||
                Build.VERSION.SDK_INT == VERSION_CODE.O && getPreviewSDKInt() > 0;
    }

    public static boolean isQ() {
        return Build.VERSION.SDK_INT > VERSION_CODE.P ||
                Build.VERSION.SDK_INT == VERSION_CODE.P && getPreviewSDKInt() > 0;
    }

    public static boolean isR() {
        return Build.VERSION.SDK_INT > VERSION_CODE.Q ||
                Build.VERSION.SDK_INT == VERSION_CODE.Q && getPreviewSDKInt() > 0;
    }

    public static boolean isS() {
        return Build.VERSION.SDK_INT > VERSION_CODE.R ||
                Build.VERSION.SDK_INT == VERSION_CODE.R && getPreviewSDKInt() > 0;
    }

    public static boolean isSL() {
        return Build.VERSION.SDK_INT > VERSION_CODE.S ||
                Build.VERSION.SDK_INT == VERSION_CODE.S && getPreviewSDKInt() > 0;
    }
}
