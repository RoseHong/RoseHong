package com.xxh.rosehong.server.pm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/21 18:23
 */
public class RhPackageSetting implements Parcelable {
    private String mPackageName;
    private int mAppId;
    private String mCpuAbi;

    public RhPackageSetting() {}

    protected RhPackageSetting(Parcel in) {
        mPackageName = in.readString();
        mAppId = in.readInt();
        mCpuAbi = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPackageName);
        dest.writeInt(mAppId);
        dest.writeString(mCpuAbi);
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }
    public String getPackageName() {
        return mPackageName;
    }

    public void setAppId(int appId) {
        mAppId = appId;
    }
    public int getAppId() {
        return mAppId;
    }

    public void setCpuAbi(String cpuAbi) {
        mCpuAbi = cpuAbi;
    }
    public String getCpuAbi() {
        return mCpuAbi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RhPackageSetting> CREATOR = new Creator<RhPackageSetting>() {
        @Override
        public RhPackageSetting createFromParcel(Parcel in) {
            return new RhPackageSetting(in);
        }

        @Override
        public RhPackageSetting[] newArray(int size) {
            return new RhPackageSetting[size];
        }
    };
}
