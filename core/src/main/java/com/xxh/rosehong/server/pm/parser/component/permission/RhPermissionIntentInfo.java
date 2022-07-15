package com.xxh.rosehong.server.pm.parser.component.permission;

import android.os.Parcel;

import com.xxh.rosehong.server.pm.parser.component.base.RhIntentInfo;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/16 0:14
 */
public class RhPermissionIntentInfo extends RhIntentInfo {
    public RhPermission permission;

    public RhPermissionIntentInfo(Object basePermissionIntentInfo) {
        super(basePermissionIntentInfo);
    }

    public RhPermissionIntentInfo(Parcel in) {
        super(in);
    }

    public void setPermission(RhPermission permission) {
        this.permission = permission;
    }
}
