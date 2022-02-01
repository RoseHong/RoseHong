package com.xxh.rosehong.utils.ref;

import com.xxh.rosehong.utils.system.RhBuild;

import java.lang.reflect.Method;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/30 上午12:10
 */
public class RhReflection {
    private static final String TAG = RhReflection.class.getSimpleName();

    public static void bypassHiddenApi() {
        if (RhBuild.isP()) {
            try {
                Method forName = Class.class.getDeclaredMethod("forName", String.class);
                Method getDeclareMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
                Class VMRuntime = (Class) forName.invoke(null, "dalvik.system.VMRuntime");
                Method getRuntime = VMRuntime.getDeclaredMethod("getRuntime");
                Object runtimeObj = getRuntime.invoke(null);
                Method setHiddenApiExemptions = (Method) getDeclareMethod.invoke(VMRuntime, "setHiddenApiExemptions", new Class[]{String[].class});
                setHiddenApiExemptions.invoke(runtimeObj, new String[][]{{"L"}});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
