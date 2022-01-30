package com.xxh.rosehongapp;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/30 下午11:11
 */
public class HowReflection {
    private static final String TAG = HowReflection.class.getSimpleName();

    //remove if/else
    private static class FiledRef<T> {
        private Field field;
        public FiledRef(Class srcClass, String fieldName) throws NoSuchFieldException {
            field = srcClass.getField(fieldName);
            field.setAccessible(true);
        }

        public T get(Object object) {
            try {
                return (T) field.get(object);
            } catch (IllegalAccessException e) {
                return null;
            }
        }

        public void set(Object object, T value) {
            try {
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ClassRef {
        public static Class init(Class ref, Class src) {
            Field[] fields = ref.getFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    try {
                        if (field.getClass().getName().equals(Field.class.getName())) {
                            //field.set(null, src.getField(field.getName()));
                            Constructor constructor = FiledRef.class.getConstructor(Class.class, String.class);
                            field.set(null, constructor.newInstance(src, field.getName()));
                        } else if (field.getClass().getName().equals(Method.class.getName())) {
                            field.set(null, src.getMethod(field.getName(), Context.class));
                        }
                        field.setAccessible(true);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            return src;
        }
    }
    private static class ApplicationRef {
        /*public static Class REF = Application.class;
        public static Field mLoadedApk;
        public static Method attach;
        static {
            try {
                mLoadedApk = Application.class.getDeclaredField("mLoadedApk");
                attach = Application.class.getDeclaredMethod("attach", Context.class);
            } catch (NoSuchFieldException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }*/

        public static Class REF = ClassRef.init(ApplicationRef.class, Application.class);
        //public static Field mLoadedApk;
        public static FiledRef<Object> mLoadedApk;
        public static Method attach;
    }
}
