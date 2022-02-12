package com.xxh.rosehong.utils.hooks.proxy;

import com.xxh.rosehong.utils.system.RhLog;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhDynamicProxy<T> {
    private static final String TAG = RhDynamicProxy.class.getSimpleName();

    private T mProxyObject = null;
    private T mBaseObject;
    private Map<String, RhDynamicProxyMethod> mDynamicProxyMethods = new HashMap<>();

    public RhDynamicProxy(T baseObject) throws Exception {
        mBaseObject = baseObject;
        if (mBaseObject.getClass().getInterfaces().length < 1) {
            RhLog.throwException(new IllegalArgumentException("dynamic proxy failed! BaseObject has no implements interfaces"));
        }
        doProxy(mBaseObject.getClass());
    }

    public void addDynamicProxyMethod(RhDynamicProxyMethod method) {
        String methodName = method.getMethodName();
        if (mDynamicProxyMethods.containsKey(methodName)) {
            RhLog.w(TAG, "method: " + methodName + ", has already proxy!");
            return;
        }
        mDynamicProxyMethods.put(methodName, method);
    }

    public T getProxyObject() {
        return mProxyObject;
    }

    private void doProxy(Class<?> baseClass) {
        mProxyObject = (T) Proxy.newProxyInstance(baseClass.getClassLoader(), getInterfaces(baseClass), (proxy, method, args) -> {
            String methodName = method.getName();
            RhDynamicProxyMethod proxyMethod = mDynamicProxyMethods.get(methodName);
            if (proxyMethod != null && proxyMethod.isEnabled()) {
                RhDynamicProxyMethodParams params = new RhDynamicProxyMethodParams(mBaseObject, method, args);
                if (!proxyMethod.beforeHookedMethod(params)) {
                    params.setResult(proxyMethod.callHookedMethod(params));
                    return proxyMethod.afterHookedMethod(params);
                } else {
                    return method.invoke(mBaseObject, args);
                }
            } else {
                return method.invoke(mBaseObject, args);
            }
        });
    }

    private Class<?>[] getInterfaces(Class<?> cls) {
        List<Class<?>> interfaceList = new ArrayList<>(1);
        do {
            for (Class<?> clazz : cls.getInterfaces()) {
                interfaceList.add(clazz);
            }
        } while ((cls=cls.getSuperclass()) != Object.class);
        Class<?>[] res = new Class[interfaceList.size()];
        interfaceList.toArray(res);
        return res;
    }
}
