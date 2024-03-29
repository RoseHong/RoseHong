package com.xxh.rosehong.utils.ref;

import com.xxh.rosehong.utils.system.RhLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/31 上午12:49
 */
public class RhMethod<T> extends RhRefBase {
    private static final String TAG = RhMethod.class.getSimpleName();

    private Method mRefMethod;

    public RhMethod(Class srcClass, Field refField) {
        try {
            Class<?>[] parameterTypes = getParams(refField);
            String methodName = resolveMethodName(refField);
            if (parameterTypes != null) {
                mRefMethod = srcClass.getDeclaredMethod(methodName, parameterTypes);
            } else {
                mRefMethod = srcClass.getDeclaredMethod(methodName);
            }
            mRefMethod.setAccessible(true);
            mIsValid = true;
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
            RhLog.w(TAG, "No method " + refField.getName() + " in class " + srcClass.getCanonicalName());
        }
    }

    //deal method overloading, Split '#' to search the real name
    public static String resolveMethodName(Field refField) {
        String methodName = refField.getName();
        if (!methodName.contains("#")) {
            return methodName;
        }
        String[] methodNames = methodName.split("#");
        if (methodNames == null || methodNames.length < 1) {
            return methodName;
        }
        return methodNames[0];
    }

    public static Class<?>[] getParams(Field field) {
        Class<?>[] res = null;
        if (field.isAnnotationPresent(RhMethodParams.class)) {
            RhMethodParams annotation = field.getAnnotation(RhMethodParams.class);
            res = annotation.value();
        } else if (field.isAnnotationPresent(RhMethodStrings.class)) {
            RhMethodStrings annotation = field.getAnnotation(RhMethodStrings.class);
            String[] classNames = annotation.value();
            if (classNames != null && classNames.length > 0) {
                res = new Class[classNames.length];
                for (int i = 0; i < classNames.length; i++) {
                    String className = classNames[i];
                    try {
                        Class<?> clazz = Class.forName(className);
                        res[i] = clazz;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return res;
    }

    public T call(Object obj, Object... parameters) {
        try {
            return (T) mRefMethod.invoke(obj, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
