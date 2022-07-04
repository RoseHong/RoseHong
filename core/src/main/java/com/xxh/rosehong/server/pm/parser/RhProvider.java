package com.xxh.rosehong.server.pm.parser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:46
 */

/**
 * 此类对应着PackageParser$Provider
 */
public class RhProvider extends RhComponent<RhIntentInfo> implements Parcelable {
    protected RhProvider(Parcel in) {
    }

    public static final Creator<RhProvider> CREATOR = new Creator<RhProvider>() {
        @Override
        public RhProvider createFromParcel(Parcel in) {
            return new RhProvider(in);
        }

        @Override
        public RhProvider[] newArray(int size) {
            return new RhProvider[size];
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
