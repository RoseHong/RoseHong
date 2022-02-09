package com.xxh.rosehong.utils.ref;

import java.lang.reflect.Field;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/29 下午6:02
 */
public class RhField<T> {
    private static final String TAG = RhField.class.getSimpleName();

    private Field mRefField;

    public RhField(Class srcClass, Field refField) {
        try {
            mRefField = srcClass.getDeclaredField(refField.getName());
            mRefField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void set(Object obj, T value) {
        try {
            mRefField.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T get(Object obj) {
        try {
            return (T) mRefField.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}