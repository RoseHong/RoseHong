package com.xxh.rosehong.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.xxh.rosehong.config.RhSystemConfig;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/21 23:34
 */
public class RhInstallResMod implements Parcelable {

    private int resCode;
    private String resDesc;

    public static RhInstallResMod success() {
        return new RhInstallResMod(RhSystemConfig.CODE_SUCCESS, "success");
    }

    public static RhInstallResMod failed(String errorMsg) {
        return new RhInstallResMod(RhSystemConfig.CODE_FAILED, errorMsg);
    }
    
    public RhInstallResMod(int resCode, String resDesc) {
        this.resCode = resCode;
        this.resDesc = resDesc;
    }

    protected RhInstallResMod(Parcel in) {
        this.resCode = in.readInt();
        this.resDesc = in.readString();
    }

    public static final Creator<RhInstallResMod> CREATOR = new Creator<RhInstallResMod>() {
        @Override
        public RhInstallResMod createFromParcel(Parcel in) {
            return new RhInstallResMod(in);
        }

        @Override
        public RhInstallResMod[] newArray(int size) {
            return new RhInstallResMod[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resCode);
        dest.writeString(this.resDesc);
    }

    public String toString() {
        return String.format("install result, resCode: %d, resDesc: %s", this.resCode, this.resDesc);
    }
}
