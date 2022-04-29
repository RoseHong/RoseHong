package com.xxh.rosehong.server.pm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/28 23:22
 */

/**
 * 此类对应着
 */
public class RhPackage implements Parcelable {
    private static final String TAG = RhPackage.class.getSimpleName();

    public String packageName;
    public String manifestPackageName;
    public String[] splitNames;

    protected RhPackage(Parcel in) {
    }

    public static final Creator<RhPackage> CREATOR = new Creator<RhPackage>() {
        @Override
        public RhPackage createFromParcel(Parcel in) {
            return new RhPackage(in);
        }

        @Override
        public RhPackage[] newArray(int size) {
            return new RhPackage[size];
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
