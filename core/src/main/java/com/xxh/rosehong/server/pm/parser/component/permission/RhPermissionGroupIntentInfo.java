package com.xxh.rosehong.server.pm.parser.component.permission;

import android.os.Parcel;

import com.xxh.rosehong.server.pm.parser.component.base.RhIntentInfo;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/16 0:22
 */
public class RhPermissionGroupIntentInfo extends RhIntentInfo {
    public RhPermissionGroup permissionGroup;

    public RhPermissionGroupIntentInfo(Object basePermissionGroupIntentInfo) {
        super(basePermissionGroupIntentInfo);
    }

    public RhPermissionGroupIntentInfo(Parcel in) {
        super(in);
    }

    public void setPermissionGroup(RhPermissionGroup permissionGroup) {
        this.permissionGroup = permissionGroup;
    }
}
