package com.xxh.rosehong.server.pm.parser.component.activity;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/12 18:27
 */

import android.os.Parcel;

import com.xxh.rosehong.server.pm.parser.component.base.RhIntentInfo;

/**
 * 此类对应着PackageParser$ActivityIntentInfo
 */
public class RhActivityIntentInfo extends RhIntentInfo {
    public RhActivity activity;

    public RhActivityIntentInfo(Object baseActivityIntentInfo) {
        super(baseActivityIntentInfo);
    }

    public RhActivityIntentInfo(Parcel in) {
        super(in);
    }

    public void setActivity(RhActivity activity) {
        this.activity = activity;
    }
}
