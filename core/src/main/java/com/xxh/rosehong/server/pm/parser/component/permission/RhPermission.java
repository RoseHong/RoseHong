package com.xxh.rosehong.server.pm.parser.component.permission;

import android.content.pm.PermissionInfo;
import android.os.Parcel;

import com.xxh.rosehong.framework.ref.android.content.pm.PackageParserRef;
import com.xxh.rosehong.server.pm.parser.RhPackage;
import com.xxh.rosehong.server.pm.parser.component.base.RhComponent;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:43
 */

/**
 * 此类对应着PackageParser$Permission
 */
public class RhPermission extends RhComponent<RhPermissionIntentInfo> {
    public PermissionInfo info;

    public RhPermission(Parcel in) {
        super(RhPermissionIntentInfo.class, in);
        info = in.readParcelable(RhPermissionIntentInfo.class.getClassLoader());
    }

    public RhPermission(Object basePermission) {
        super(RhPermissionIntentInfo.class, basePermission);
        info = PackageParserRef.PermissionRef.info.get(basePermission);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(info, 0);
    }

    public static RhPermission make(RhPackage owner, Object basePermission) {
        RhPermission rhPermission = new RhPermission(basePermission);
        rhPermission.owner = owner;
        if (rhPermission.intents != null) {
            for (RhPermissionIntentInfo intentInfo : rhPermission.intents) {
                intentInfo.setPermission(rhPermission);
            }
        }
        return rhPermission;
    }
}
