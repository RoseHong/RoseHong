package com.xxh.rosehong.server.pm.parser.component.service;

import android.content.pm.ServiceInfo;
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
 * 此类对应着PackageParser$Service
 */
public class RhService extends RhComponent<RhServiceIntentInfo> {
    public ServiceInfo info;

    public RhService(Parcel in) {
        super(RhServiceIntentInfo.class, in);
        info = in.readParcelable(RhServiceIntentInfo.class.getClassLoader());
    }

    public RhService(Object baseService) {
        super(RhServiceIntentInfo.class, baseService);
        info = PackageParserRef.ServiceRef.info.get(baseService);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(info, 0);
    }

    public static RhService make(RhPackage owner, Object baseService) {
        RhService rhService = new RhService(baseService);
        rhService.owner = owner;
        if (rhService.intents != null) {
            for (RhServiceIntentInfo intentInfo : rhService.intents) {
                intentInfo.setService(rhService);
            }
        }
        return rhService;
    }
}
