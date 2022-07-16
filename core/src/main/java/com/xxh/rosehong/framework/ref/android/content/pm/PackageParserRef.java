package com.xxh.rosehong.framework.ref.android.content.pm;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.Bundle;

import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhConstructor;
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

    public static RhConstructor<Object> constructor;

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

        public static RhField<ArrayList<Object>> permissions;
        public static RhField<ArrayList<Object>> permissionGroups;
        public static RhField<ArrayList<Object>> activities;
        public static RhField<ArrayList<Object>> receivers;
        public static RhField<ArrayList<Object>> providers;
        public static RhField<ArrayList<Object>> services;
        public static RhField<ArrayList<Object>> instrumentation;

        public static RhField<Integer> mVersionCode;
        public static RhField<String> mVersionName;
        public static RhField<String> mSharedUserId;
        public static RhField<Integer> mSharedUserLabel;
        public static RhField<Object> mSigningDetails;
        public static RhField<Object> mExtras;

        public static RhField<Boolean> use32bitAbi;
        public static RhField<Bundle> mAppMetaData;
        public static RhField<Integer> mPreferredOrder;
        public static RhField<ArrayList<String>> requestedPermissions;
        public static RhField<ArrayList<String>> usesLibraries;
        public static RhField<ArrayList<String>> usesOptionalLibraries;
        public static RhField<ArrayList<ConfigurationInfo>> configPreferences;
        public static RhField<ArrayList<FeatureInfo>> reqFeatures;
        public static RhField<Signature[]> mSignatures;
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

    public static class InstrumentationRef {
        public static Class<?> REF = RhClass.init(ServiceRef.class, "android.content.pm.PackageParser$Instrumentation");
        public static RhField<InstrumentationInfo> info;
    }

    public static class IntentInfoRef {
        public static Class<?> REF = RhClass.init(IntentInfoRef.class, "android.content.pm.PackageParser$IntentInfo");
        public static RhField<Boolean> hasDefault;
        public static RhField<Integer> labelRes;
        public static RhField<CharSequence> nonLocalizedLabel;
        public static RhField<Integer> icon;
        public static RhField<Integer> logo;
        public static RhField<Integer> banner;
        public static RhField<Integer> preferred;
    }

    public static class ComponentRef {
        public static Class<?> REF = RhClass.init(ComponentRef.class, "android.content.pm.PackageParser$Component");
        public static RhField<ArrayList> intents;
        public static RhField<String> className;
        public static RhField<Bundle> metaData;
        public static RhField<Object> owner;
        public static RhField<Integer> order;
        public static RhField<ComponentName> componentName;
        public static RhField<String> componentShortName;
    }
}
