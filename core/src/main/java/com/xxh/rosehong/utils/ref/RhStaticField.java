package com.xxh.rosehong.utils.ref;

import java.lang.reflect.Field;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/31 上午12:36
 */
public class RhStaticField<T> {
    private static final String TAG = RhStaticField.class.getSimpleName();

    private Field refField;

    public RhStaticField(Class srcClass, String fieldName) {
        try {
            refField = srcClass.getField(fieldName);
            refField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void set(T value) {
        try {
            refField.set(null, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T get() {
        try {
            return (T) refField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
