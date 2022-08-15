package com.xxh.rosehong.framework.ref.android.content.pm;

import android.content.pm.SharedLibraryInfo;
import android.content.pm.VersionedPackage;

import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhConstructor;
import com.xxh.rosehong.utils.ref.RhMethodParams;

import java.util.List;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/16 0:08
 */
public class SharedLibraryInfoRef {
    public static Class<?> REF = RhClass.init(SharedLibraryInfoRef.class, SharedLibraryInfo.class);
    @RhMethodParams({String.class, String.class, List.class,
            String.class, long.class, int.class,
            VersionedPackage.class, List.class,
            List.class, boolean.class})
    public static RhConstructor<SharedLibraryInfo> constructor;
}
