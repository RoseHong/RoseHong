package com.xxh.rosehong.utils.ref;

import com.xxh.rosehong.utils.system.RhLog;

import java.lang.reflect.Field;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/31 上午12:36
 */
public class RhStaticField<T> extends RhRefBase {
    private static final String TAG = RhStaticField.class.getSimpleName();

    private Field mRefStaticField;

    public RhStaticField(Class srcClass, Field refField) {
        try {
            mRefStaticField = srcClass.getDeclaredField(refField.getName());
            mRefStaticField.setAccessible(true);
            mIsValid = true;
        } catch (NoSuchFieldException e) {
            //e.printStackTrace();
            RhLog.w(TAG, "No static field " + refField.getName() + " in class " + srcClass.getCanonicalName());
        }
    }

    public void set(T value) {
        try {
            mRefStaticField.set(null, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public T get() {
        try {
            return (T) mRefStaticField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
