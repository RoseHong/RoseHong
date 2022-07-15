package com.xxh.rosehong.server.pm.parser.component.permission;

import android.content.pm.PermissionGroupInfo;
import android.os.Parcel;

import com.xxh.rosehong.framework.ref.android.content.pm.PackageParserRef;
import com.xxh.rosehong.server.pm.parser.RhPackage;
import com.xxh.rosehong.server.pm.parser.component.base.RhComponent;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:44
 */

/**
 * 此类对应着PackageParser$PermissionGroup
 */
public class RhPermissionGroup extends RhComponent<RhPermissionGroupIntentInfo> {
    public PermissionGroupInfo info;

    public RhPermissionGroup(Parcel in) {
        super(RhPermissionGroupIntentInfo.class, in);
        info = in.readParcelable(RhPermissionIntentInfo.class.getClassLoader());
    }

    public RhPermissionGroup(Object basePermissionGroup) {
        super(RhPermissionGroupIntentInfo.class, basePermissionGroup);
        info = PackageParserRef.PermissionGroupRef.info.get(basePermissionGroup);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(info, 0);
    }

    public static RhPermissionGroup make(RhPackage owner, Object basePermissionGroup) {
        RhPermissionGroup rhPermissionGroup = new RhPermissionGroup(basePermissionGroup);
        rhPermissionGroup.owner = owner;
        if (rhPermissionGroup.intents != null) {
            for (RhPermissionGroupIntentInfo intentInfo : rhPermissionGroup.intents) {
                intentInfo.setPermissionGroup(rhPermissionGroup);
            }
        }
        return rhPermissionGroup;
    }
}
