package com.xxh.rosehong.server.pm.parser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:46
 */

/**
 * 此类对应着PackageParser$Service
 */
public class RhService extends RhComponent<RhIntentInfo> implements Parcelable {
    protected RhService(Parcel in) {
    }

    public static final Creator<RhService> CREATOR = new Creator<RhService>() {
        @Override
        public RhService createFromParcel(Parcel in) {
            return new RhService(in);
        }

        @Override
        public RhService[] newArray(int size) {
            return new RhService[size];
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
