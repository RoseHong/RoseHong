package com.xxh.rosehong.utils.ref;

import com.xxh.rosehong.utils.system.RhLog;

import java.lang.reflect.Field;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/29 下午6:02
 */
public class RhField<T> extends RhRefBase {
    private static final String TAG = RhField.class.getSimpleName();

    private Field mRefField;

    public RhField(Class srcClass, Field refField) {
        try {
            mRefField = srcClass.getDeclaredField(refField.getName());
            mRefField.setAccessible(true);
            mIsValid = true;
        } catch (NoSuchFieldException e) {
            //e.printStackTrace();
            RhLog.w(TAG, "No field " + refField.getName() + " in class " + srcClass.getCanonicalName());
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