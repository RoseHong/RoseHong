package com.xxh.rosehong.utils.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/1 下午5:45
 */
public class RhConstructor<T> {
    private static final String TAG = RhConstructor.class.getSimpleName();

    private Constructor<?> mRefConstructor;

    public RhConstructor(Class<?> srcClass, Field refField) {
        Class<?>[] parameterTypes = RhMethod.getParams(refField);
        try {
            if (parameterTypes != null) {
                mRefConstructor = srcClass.getConstructor(parameterTypes);
            } else {
                mRefConstructor = srcClass.getConstructor();
            }
            mRefConstructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public T newInstance(Object... parameters) {
        try {
            return (T) mRefConstructor.newInstance(parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
