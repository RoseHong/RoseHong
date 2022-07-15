package com.xxh.rosehong.server.pm.parser.component.instrumentation;

import android.os.Parcel;

import com.xxh.rosehong.server.pm.parser.component.base.RhIntentInfo;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/16 0:34
 */
public class RhInstrumentationIntentInfo extends RhIntentInfo {
    public RhInstrumentation instrumentation;

    public RhInstrumentationIntentInfo(Object baseInstrumentationIntentInfo) {
        super(baseInstrumentationIntentInfo);
    }

    public RhInstrumentationIntentInfo(Parcel in) {
        super(in);
    }

    public void setInstrumentation(RhInstrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }
}
