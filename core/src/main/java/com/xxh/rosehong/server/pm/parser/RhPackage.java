package com.xxh.rosehong.server.pm.parser;

import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.xxh.rosehong.server.pm.parser.component.activity.RhActivity;
import com.xxh.rosehong.server.pm.parser.component.instrumentation.RhInstrumentation;
import com.xxh.rosehong.server.pm.parser.component.permission.RhPermission;
import com.xxh.rosehong.server.pm.parser.component.permission.RhPermissionGroup;
import com.xxh.rosehong.server.pm.parser.component.provider.RhProvider;
import com.xxh.rosehong.server.pm.parser.component.service.RhService;

import java.util.ArrayList;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/28 23:22
 */

/**
 * 此类对应着PackageParser$Package
 */
public class RhPackage implements Parcelable {
    private static final String TAG = RhPackage.class.getSimpleName();

    public String packageName;
    public String[] splitNames;
    public String codePath;
    public String baseCodePath;
    public String[] splitCodePaths;
    public String mSharedUserId;
    public int mVersionCode;
    public String mVersionName;
    public int mSharedUserLabel;
    public Object mSigningDetails;
    public Object mExtras;
    public boolean use32bitAbi;
    public Bundle mAppMetaData;
    public int mPreferredOrder;

    public ApplicationInfo applicationInfo;
    public ArrayList<RhPermission> permissions = new ArrayList<>(0);
    public ArrayList<RhPermissionGroup> permissionGroups = new ArrayList<>(0);
    public ArrayList<RhActivity> activities = new ArrayList<>(0);
    public ArrayList<RhActivity> receivers = new ArrayList<>(0);
    public ArrayList<RhProvider> providers = new ArrayList<>(0);
    public ArrayList<RhService> services = new ArrayList<>(0);
    public ArrayList<RhInstrumentation> instrumentation = new ArrayList<>(0);

    public ArrayList<String> requestedPermissions = new ArrayList<>();
    public ArrayList<String> usesLibraries;
    public ArrayList<String> usesOptionalLibraries;
    public ArrayList<ConfigurationInfo> configPreferences;
    public ArrayList<FeatureInfo> reqFeatures;
    public Signature[] mSignatures;

    public RhPackage() {}

    protected RhPackage(Parcel in) {

    }

    public boolean isSplitApk() {
        if (splitNames == null || splitNames.length < 1) {
            return false;
        }
        return true;
    }

    public static final Creator<RhPackage> CREATOR = new Creator<RhPackage>() {
        @Override
        public RhPackage createFromParcel(Parcel in) {
            return new RhPackage(in);
        }

        @Override
        public RhPackage[] newArray(int size) {
            return new RhPackage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
