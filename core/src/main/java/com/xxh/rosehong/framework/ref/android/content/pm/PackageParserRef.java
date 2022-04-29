package com.xxh.rosehong.framework.ref.android.content.pm;

import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhMethod;
import com.xxh.rosehong.utils.ref.RhMethodParams;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/28 23:55
 */
public class PackageParserRef {
    public static Class<?> REF = RhClass.init(PackageParserRef.class, "android.content.pm.PackageParser");

    @RhMethodParams({String.class, int.class})
    public static RhMethod<Object> parsePackage;

    public static class PackageRef {
        public static Class<?> REF = RhClass.init(PackageRef.class, "android.content.pm.PackageParser$Package");
    }
}
