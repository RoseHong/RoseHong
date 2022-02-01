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

    private Constructor<?> refConstructor;

    public RhConstructor(Class<?> srcClass, Field refField) {
        Class<?>[] parameterTypes = RhMethod.getParams(refField);
        try {
            if (parameterTypes != null) {
                refConstructor = srcClass.getConstructor(parameterTypes);
            } else {
                refConstructor = srcClass.getConstructor();
            }
            refConstructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public T newInstance(Object... parameters) {
        try {
            return (T) refConstructor.newInstance(parameters);
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
