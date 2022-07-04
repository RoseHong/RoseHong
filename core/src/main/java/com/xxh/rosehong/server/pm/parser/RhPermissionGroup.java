package com.xxh.rosehong.server.pm.parser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:44
 */

/**
 * 此类对应着PackageParser$PermissionGroup
 */
public class RhPermissionGroup extends RhComponent<RhIntentInfo> implements Parcelable {
    protected RhPermissionGroup(Parcel in) {
    }

    public static final Creator<RhPermissionGroup> CREATOR = new Creator<RhPermissionGroup>() {
        @Override
        public RhPermissionGroup createFromParcel(Parcel in) {
            return new RhPermissionGroup(in);
        }

        @Override
        public RhPermissionGroup[] newArray(int size) {
            return new RhPermissionGroup[size];
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
