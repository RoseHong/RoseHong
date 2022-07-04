package com.xxh.rosehong.server.pm.parser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:45
 */

/**
 * 此类对应着PackageParser$Activity
 */
public class RhActivity extends RhComponent<RhIntentInfo> implements Parcelable {
    protected RhActivity(Parcel in) {
    }

    public static final Creator<RhActivity> CREATOR = new Creator<RhActivity>() {
        @Override
        public RhActivity createFromParcel(Parcel in) {
            return new RhActivity(in);
        }

        @Override
        public RhActivity[] newArray(int size) {
            return new RhActivity[size];
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
