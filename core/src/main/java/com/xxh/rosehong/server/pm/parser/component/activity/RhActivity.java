package com.xxh.rosehong.server.pm.parser.component.activity;

import android.content.pm.ActivityInfo;
import android.os.Parcel;

import com.xxh.rosehong.framework.ref.android.content.pm.PackageParserRef.ActivityRef;
import com.xxh.rosehong.server.pm.parser.RhPackage;
import com.xxh.rosehong.server.pm.parser.component.base.RhComponent;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:45
 */

/**
 * 此类对应着PackageParser$Activity
 */
public class RhActivity extends RhComponent<RhActivityIntentInfo> {
    public ActivityInfo info;

    public RhActivity(Parcel in) {
        super(RhActivityIntentInfo.class, in);
        info = in.readParcelable(ActivityInfo.class.getClassLoader());
    }

    public RhActivity(Object baseActivity) {
        super(RhActivityIntentInfo.class, baseActivity);
        info = ActivityRef.info.get(baseActivity);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(info, 0);
    }

    public static RhActivity make(RhPackage owner, Object baseActivity) {
        RhActivity rhActivity = new RhActivity(baseActivity);
        rhActivity.owner = owner;
        if (rhActivity.intents != null) {
            for (RhActivityIntentInfo intentInfo : rhActivity.intents) {
                intentInfo.setActivity(rhActivity);
            }
        }
        return rhActivity;
    }
}
