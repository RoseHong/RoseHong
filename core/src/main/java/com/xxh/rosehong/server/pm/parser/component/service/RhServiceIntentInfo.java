package com.xxh.rosehong.server.pm.parser.component.service;

import android.os.Parcel;

import com.xxh.rosehong.server.pm.parser.component.base.RhIntentInfo;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/16 0:35
 */
public class RhServiceIntentInfo extends RhIntentInfo {
    public RhService service;

    public RhServiceIntentInfo(Object baseServiceIntentInfo) {
        super(baseServiceIntentInfo);
    }

    public RhServiceIntentInfo(Parcel in) {
        super(in);
    }

    public void setService(RhService service) {
        this.service = service;
    }
}
