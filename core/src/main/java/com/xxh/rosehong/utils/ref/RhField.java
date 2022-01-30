package com.xxh.rosehong.utils.ref;

import java.lang.reflect.Field;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/29 下午6:02
 */
public class RhField<T> {
    private static final String TAG = RhField.class.getSimpleName();

    private Field refField;

    public RhField(Class srcClass, String fieldName) {
        try {
            refField = srcClass.getField(fieldName);
            refField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void set(Object obj, T value) {
        try {
            refField.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T get(Object obj) {
        try {
            return (T) refField.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}