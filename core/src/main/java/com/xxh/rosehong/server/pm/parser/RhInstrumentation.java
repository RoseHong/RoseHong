package com.xxh.rosehong.server.pm.parser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:47
 */

/**
 * 此类对应着PackageParser$Instrumentation
 */
public class RhInstrumentation extends RhComponent<RhIntentInfo> implements Parcelable {
    protected RhInstrumentation(Parcel in) {
    }

    public static final Creator<RhInstrumentation> CREATOR = new Creator<RhInstrumentation>() {
        @Override
        public RhInstrumentation createFromParcel(Parcel in) {
            return new RhInstrumentation(in);
        }

        @Override
        public RhInstrumentation[] newArray(int size) {
            return new RhInstrumentation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
