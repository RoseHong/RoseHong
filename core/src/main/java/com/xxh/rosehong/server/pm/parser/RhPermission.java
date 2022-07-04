package com.xxh.rosehong.server.pm.parser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:43
 */

/**
 * 此类对应着PackageParser$Permission
 */
public class RhPermission extends RhComponent<RhIntentInfo> implements Parcelable {
    protected RhPermission(Parcel in) {
    }

    public static final Creator<RhPermission> CREATOR = new Creator<RhPermission>() {
        @Override
        public RhPermission createFromParcel(Parcel in) {
            return new RhPermission(in);
        }

        @Override
        public RhPermission[] newArray(int size) {
            return new RhPermission[size];
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
