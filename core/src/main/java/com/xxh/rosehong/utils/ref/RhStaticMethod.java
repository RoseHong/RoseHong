package com.xxh.rosehong.utils.ref;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/1 下午5:34
 */
public class RhStaticMethod<T> {
    private static final String TAG = RhStaticMethod.class.getSimpleName();

    private Method mRefStaticMethod;

    public RhStaticMethod(Class srcClass, Field refField) {
        Class<?>[] parameterTypes = RhMethod.getParams(refField);
        String staticMethodName = RhMethod.resolveMethodName(refField);
        try {
            if (parameterTypes != null) {
                mRefStaticMethod = srcClass.getDeclaredMethod(staticMethodName, parameterTypes);
            } else {
                mRefStaticMethod = srcClass.getDeclaredMethod(staticMethodName);
            }
            mRefStaticMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public T call(Object... parameters) {
        try {
            return (T) mRefStaticMethod.invoke(null, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
