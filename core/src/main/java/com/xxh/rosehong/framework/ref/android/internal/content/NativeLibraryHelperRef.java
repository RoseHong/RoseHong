package com.xxh.rosehong.framework.ref.android.internal.content;

import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhField;
import com.xxh.rosehong.utils.ref.RhMethodParams;
import com.xxh.rosehong.utils.ref.RhMethodStrings;
import com.xxh.rosehong.utils.ref.RhStaticMethod;

import java.io.File;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/17 16:22
 */
public class NativeLibraryHelperRef {
    public static Class<?> REF = RhClass.init(NativeLibraryHelperRef.class, "com.android.internal.content.NativeLibraryHelper");
    @RhMethodStrings({"com.android.internal.content.NativeLibraryHelper$Handle", "[Ljava.lang.String;"})
    public static RhStaticMethod<Integer> findSupportedAbi;
    @RhMethodStrings({"com.android.internal.content.NativeLibraryHelper$Handle", "java.io.File", "java.lang.String"})
    public static RhStaticMethod<Integer> copyNativeBinaries;

    public static class HandleRef {
        public static Class<?> REF = RhClass.init(HandleRef.class, "com.android.internal.content.NativeLibraryHelper$Handle");
        public static RhField<Boolean> extractNativeLibs;
        @RhMethodParams(File.class)
        public static RhStaticMethod<Object> create;
    }
}
