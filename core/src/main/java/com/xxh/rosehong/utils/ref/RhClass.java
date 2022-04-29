package com.xxh.rosehong.utils.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/29 下午6:02
 */
public class RhClass {
    private static final String TAG = RhClass.class.getSimpleName();

    private static Map<Class, Constructor> mInterfaceMap = new HashMap<>(5);
    static {
        try {
            mInterfaceMap.put(RhField.class, RhField.class.getConstructor(Class.class, Field.class));
            mInterfaceMap.put(RhStaticField.class, RhStaticField.class.getConstructor(Class.class, Field.class));
            mInterfaceMap.put(RhMethod.class, RhMethod.class.getConstructor(Class.class, Field.class));
            mInterfaceMap.put(RhStaticMethod.class, RhStaticMethod.class.getConstructor(Class.class, Field.class));
            mInterfaceMap.put(RhConstructor.class, RhConstructor.class.getConstructor(Class.class, Field.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Class<?> init(Class refClass, String srcClassName) {
        try {
            Class srcClass = Class.forName(srcClassName);
            return init(refClass, srcClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> init(Class refClass, Class srcClass) {
        for (Field field : refClass.getFields()) {
            if (Modifier.isStatic(field.getModifiers()) && mInterfaceMap.containsKey(field.getType())) {
                Constructor constructor = mInterfaceMap.get(field.getType());
                try {
                    field.set(null, constructor.newInstance(srcClass, field));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return srcClass;
    }
}