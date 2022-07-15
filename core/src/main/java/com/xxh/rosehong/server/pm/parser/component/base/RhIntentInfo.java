package com.xxh.rosehong.server.pm.parser.component.base;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:41
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.xxh.rosehong.framework.ref.android.content.pm.PackageParserRef.IntentInfoRef;

/**
 * 此类对应着PackageParser$IntentInfo
 */
public class RhIntentInfo implements Parcelable {

    public boolean hasDefault;
    public int labelRes;
    public CharSequence nonLocalizedLabel;
    public int icon;
    public int logo;
    public int banner;
    public int preferred;

    public RhIntentInfo(Object baseIntentInfo) {
        hasDefault = IntentInfoRef.hasDefault.get(baseIntentInfo);
        labelRes = IntentInfoRef.labelRes.get(baseIntentInfo);
        nonLocalizedLabel = IntentInfoRef.nonLocalizedLabel.get(baseIntentInfo);
        icon = IntentInfoRef.icon.get(baseIntentInfo);
        logo = IntentInfoRef.logo.get(baseIntentInfo);
        banner = IntentInfoRef.banner.get(baseIntentInfo);
        preferred = IntentInfoRef.preferred.get(baseIntentInfo);
    }

    public RhIntentInfo(Parcel in) {
        hasDefault = in.readByte() != 0;
        labelRes = in.readInt();
        nonLocalizedLabel = in.readString();
        icon = in.readInt();
        logo = in.readInt();
        banner = in.readInt();
        preferred = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (hasDefault ? 1 : 0));
        dest.writeInt(labelRes);
        dest.writeString(nonLocalizedLabel.toString());
        dest.writeInt(icon);
        dest.writeInt(logo);
        dest.writeInt(banner);
        dest.writeInt(preferred);
    }

    public static final Creator<RhIntentInfo> CREATOR = new Creator<RhIntentInfo>() {
        @Override
        public RhIntentInfo createFromParcel(Parcel in) {
            return new RhIntentInfo(in);
        }

        @Override
        public RhIntentInfo[] newArray(int size) {
            return new RhIntentInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
