package com.xxh.rosehong.framework.ref.android.content.pm;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;

import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhField;
import com.xxh.rosehong.utils.ref.RhMethod;
import com.xxh.rosehong.utils.ref.RhMethodParams;

import java.util.ArrayList;

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
        public static RhField<String> packageName;
        public static RhField<String[]> splitNames;
        public static RhField<String> codePath;
        public static RhField<String> baseCodePath;
        public static RhField<String[]> splitCodePaths;
        public static RhField<ApplicationInfo> applicationInfo;

        public static RhField<ArrayList> permissions;
        public static RhField<ArrayList> permissionGroups;
        public static RhField<ArrayList> activities;
        public static RhField<ArrayList> receivers;
        public static RhField<ArrayList> providers;
        public static RhField<ArrayList> services;

        public static RhField<Integer> mVersionCode;
        public static RhField<String> mVersionName;
        public static RhField<String> mSharedUserId;
        public static RhField<Integer> mSharedUserLabel;
        public static RhField<Object> mSigningDetails;
        public static RhField<Object> mExtras;

        public static RhField<Integer> mCompileSdkVersion;
        public static RhField<Boolean> use32bitAbi;
    }

    public static class PermissionRef {
        public static Class<?> REF = RhClass.init(PermissionRef.class, "android.content.pm.PackageParser$Permission");
        public static RhField<PermissionInfo> info;
    }

    public static class PermissionGroupRef {
        public static Class<?> REF = RhClass.init(PermissionGroupRef.class, "android.content.pm.PackageParser$PermissionGroup");
        public static RhField<PermissionGroupInfo> info;
    }

    public static class ActivityRef {
        public static Class<?> REF = RhClass.init(ActivityRef.class, "android.content.pm.PackageParser$Activity");
        public static RhField<ActivityInfo> info;
    }

    public static class ProviderRef {
        public static Class<?> REF = RhClass.init(ProviderRef.class, "android.content.pm.PackageParser$Provider");
        public static RhField<ProviderInfo> info;
    }

    public static class ServiceRef {
        public static Class<?> REF = RhClass.init(ServiceRef.class, "android.content.pm.PackageParser$Service");
        public static RhField<ServiceInfo> info;
    }
}
