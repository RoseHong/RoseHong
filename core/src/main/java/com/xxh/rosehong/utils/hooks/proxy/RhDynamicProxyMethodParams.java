package com.xxh.rosehong.utils.hooks.proxy;

import java.lang.reflect.Method;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 下午9:50
 */
public class RhDynamicProxyMethodParams {
    private static final String TAG = RhDynamicProxyMethodParams.class.getSimpleName();

    public Object thisObject;
    public Method originMethod;
    public Object[] args;
    public Object result;

    public RhDynamicProxyMethodParams(Object thisObject, Method originMethod, Object[] args) {
        this.thisObject = thisObject;
        this.originMethod = originMethod;
        this.args = args;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
