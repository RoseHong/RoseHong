package com.xxh.rosehong.framework.ref.android.content.pm;

import android.content.pm.ApplicationInfo;
import android.content.pm.SharedLibraryInfo;

import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhField;

import java.util.List;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/16 0:13
 */
public class ApplicationInfoRef {
    public static Class<?> REF = RhClass.init(ApplicationInfoRef.class, ApplicationInfo.class);
    public static RhField<List<SharedLibraryInfo>> sharedLibraryInfos;
}
