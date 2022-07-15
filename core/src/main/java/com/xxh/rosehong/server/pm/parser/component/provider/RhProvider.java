package com.xxh.rosehong.server.pm.parser.component.provider;

import android.content.pm.ProviderInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.xxh.rosehong.framework.ref.android.content.pm.PackageParserRef;
import com.xxh.rosehong.server.pm.parser.RhPackage;
import com.xxh.rosehong.server.pm.parser.component.base.RhComponent;
import com.xxh.rosehong.server.pm.parser.component.base.RhIntentInfo;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:46
 */

/**
 * 此类对应着PackageParser$Provider
 */
public class RhProvider extends RhComponent<RhProviderIntentInfo> {
    public ProviderInfo info;

    public RhProvider(Parcel in) {
        super(RhProviderIntentInfo.class, in);
        info = in.readParcelable(RhProviderIntentInfo.class.getClassLoader());
    }

    public RhProvider(Object baseProvider) {
        super(RhProviderIntentInfo.class, baseProvider);
        info = PackageParserRef.ProviderRef.info.get(baseProvider);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(info, 0);
    }

    public static RhProvider make(RhPackage owner, Object baseProvider) {
        RhProvider rhProvider = new RhProvider(baseProvider);
        rhProvider.owner = owner;
        if (rhProvider.intents != null) {
            for (RhProviderIntentInfo intentInfo : rhProvider.intents) {
                intentInfo.setProvider(rhProvider);
            }
        }
        return rhProvider;
    }
}
