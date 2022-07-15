package com.xxh.rosehong.server.pm.parser.component.provider;

import android.os.Parcel;

import com.xxh.rosehong.server.pm.parser.component.base.RhIntentInfo;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/16 0:34
 */
public class RhProviderIntentInfo extends RhIntentInfo {
    public RhProvider provider;

    public RhProviderIntentInfo(Object baseProviderIntentInfo) {
        super(baseProviderIntentInfo);
    }

    public RhProviderIntentInfo(Parcel in) {
        super(in);
    }

    public void setProvider(RhProvider provider) {
        this.provider = provider;
    }
}
