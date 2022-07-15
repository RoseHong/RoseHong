package com.xxh.rosehong.server.pm.parser.component.instrumentation;

import android.content.pm.InstrumentationInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.xxh.rosehong.framework.ref.android.content.pm.PackageParserRef;
import com.xxh.rosehong.server.pm.parser.RhPackage;
import com.xxh.rosehong.server.pm.parser.component.base.RhComponent;
import com.xxh.rosehong.server.pm.parser.component.base.RhIntentInfo;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:47
 */

/**
 * 此类对应着PackageParser$Instrumentation
 */
public class RhInstrumentation extends RhComponent<RhInstrumentationIntentInfo> {
    public InstrumentationInfo info;

    public RhInstrumentation(Parcel in) {
        super(RhInstrumentationIntentInfo.class, in);
        info = in.readParcelable(RhInstrumentationIntentInfo.class.getClassLoader());
    }

    public RhInstrumentation(Object baseInstrumentation) {
        super(RhInstrumentationIntentInfo.class, baseInstrumentation);
        info = PackageParserRef.InstrumentationRef.info.get(baseInstrumentation);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(info, 0);
    }

    public static RhInstrumentation make(RhPackage owner, Object baseInstrumentation) {
        RhInstrumentation rhInstrumentation = new RhInstrumentation(baseInstrumentation);
        rhInstrumentation.owner = owner;
        if (rhInstrumentation.intents != null) {
            for (RhInstrumentationIntentInfo intentInfo : rhInstrumentation.intents) {
                intentInfo.setInstrumentation(rhInstrumentation);
            }
        }
        return rhInstrumentation;
    }
}
