package com.xxh.rosehong.utils.hooks.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 下午2:38
 */
public class RhDynamicProxyMethod {
    private static final String TAG = RhDynamicProxyMethod.class.getSimpleName();
    private String mMethodName;
    private boolean mIsEnabled = true;

    public RhDynamicProxyMethod(String methodName) {
        mMethodName = methodName;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        mIsEnabled = isEnabled;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public boolean beforeHookedMethod(RhDynamicProxyMethodParams params) {
        return false;
    }

    public Object callHookedMethod(RhDynamicProxyMethodParams params) throws InvocationTargetException, IllegalAccessException {
        return params.originMethod.invoke(params.thisObject, params.args);
    }

    public Object afterHookedMethod(RhDynamicProxyMethodParams params) {
        return params.result;
    }
}
