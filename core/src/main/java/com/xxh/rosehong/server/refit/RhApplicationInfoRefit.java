package com.xxh.rosehong.server.refit;

import android.content.pm.ApplicationInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.VersionedPackage;
import android.os.Build;

import com.xxh.rosehong.framework.ref.android.content.pm.ApplicationInfoRef;
import com.xxh.rosehong.framework.ref.android.content.pm.SharedLibraryInfoRef;
import com.xxh.rosehong.framework.simple.SystemConfig;
import com.xxh.rosehong.utils.system.RhBuild;
import com.xxh.rosehong.utils.system.RhLog;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/19 22:52
 */
public class RhApplicationInfoRefit {
    private static final String TAG = RhApplicationInfoRefit.class.getSimpleName();

    private static final String ANDROID_TEST_BASE_NAME = "android.test.base";
    private static final String ANDROID_TEST_RUNNER_NAME = "android.test.runner";
    private static final String ORG_APACHE_HTTP_LEGACY_NAME = "org.apache.http.legacy";
    private static final String ORG_APACHE_HTTP_LEGACY_JAR = "/system/framework/org.apache.http.legacy.jar";

    private ApplicationInfo mApplicationInfo;
    private ArrayList<String> mAllUsesLibraries = new ArrayList<>();

    public RhApplicationInfoRefit(ApplicationInfo applicationInfo, ArrayList<String>... usesLibraries) {
        mApplicationInfo = applicationInfo;
        if (usesLibraries != null && usesLibraries.length > 0) {
            for (ArrayList<String> library : usesLibraries) {
                if (library != null) {
                    Iterator<String> it = library.iterator(); // 使用迭代器遍历，效率会高很多
                    while (it.hasNext()) {
                        String name = it.next();
                        if (!mAllUsesLibraries.contains(name)) {
                            mAllUsesLibraries.add(name);
                        }
                    }
                }
            }
        }
    }

    public ApplicationInfo doRefit() {
        dealSharedLibraryList();
        return mApplicationInfo;
    }

    private void dealSharedLibraryList() {
        // 处理apache
        if (!mAllUsesLibraries.contains(ORG_APACHE_HTTP_LEGACY_NAME) && mApplicationInfo.targetSdkVersion < Build.VERSION_CODES.M) {
            mAllUsesLibraries.add(ORG_APACHE_HTTP_LEGACY_NAME);
        }

        // 处理无法找到junit.framework.Assert
        if (mAllUsesLibraries.contains(ANDROID_TEST_RUNNER_NAME) || (RhBuild.isR() && mApplicationInfo.targetSdkVersion < 30)) {
            mAllUsesLibraries.add(ANDROID_TEST_BASE_NAME);
        }

        ArrayList<String> sharedLibraryFiles = new ArrayList<>();
        ArrayList<SharedLibraryInfo> sharedLibraryInfos = new ArrayList<>();
        Iterator<String> iterator = mAllUsesLibraries.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            SystemConfig.SharedLibraryEntry entry = SystemConfig.getInstance().getSharedLibrariesEntry(name);
            if (entry == null) {
                if (ORG_APACHE_HTTP_LEGACY_NAME.equals(name)) {
                    // apache这种情况下得自带
                    entry = new SystemConfig.SharedLibraryEntry(name, ORG_APACHE_HTTP_LEGACY_JAR, null);
                } else {
                    RhLog.e(TAG, "system no this shared library: " + name);
                    continue;
                }
            }
            sharedLibraryFiles.add(entry.filename);
            sharedLibraryInfos.add(SharedLibraryInfoRef.constructor.newInstance(ORG_APACHE_HTTP_LEGACY_JAR, null, null,
                    ORG_APACHE_HTTP_LEGACY_NAME, -1, 0, new VersionedPackage("android", 0), null, null, false));
        }
        if (!sharedLibraryFiles.isEmpty()) {
            mApplicationInfo.sharedLibraryFiles = sharedLibraryFiles.toArray(new String[0]);
        }
        if (!sharedLibraryInfos.isEmpty()) {
            ApplicationInfoRef.sharedLibraryInfos.set(mApplicationInfo, sharedLibraryInfos);
        }
    }
}
