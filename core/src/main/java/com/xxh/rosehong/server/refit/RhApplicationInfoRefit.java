package com.xxh.rosehong.server.refit;

import android.content.pm.ApplicationInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.VersionedPackage;
import android.os.Build;

import com.xxh.rosehong.config.RhCustomConfig;
import com.xxh.rosehong.framework.ref.android.content.pm.ApplicationInfoRef;
import com.xxh.rosehong.framework.ref.android.content.pm.SharedLibraryInfoRef;
import com.xxh.rosehong.framework.simple.SystemConfigSimple;
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
    private ArrayList<String> mAllUseLibraries = new ArrayList<>();
    private String mCpuAbi;
    private boolean mIsSplitApk = false;
    private String[] mSplitCodePaths;

    public void setSplitCodePaths(String[] splitCodePaths) {
        mSplitCodePaths = splitCodePaths;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        mApplicationInfo = applicationInfo;
    }

    public void setCpuAbi(String cpuAbi) {
        mCpuAbi = cpuAbi;
    }

    public void setSplitApk(boolean isSplitApk) {
        mIsSplitApk = isSplitApk;
    }

    public void setUseLibraries(ArrayList<String>... useLibraries) {
        if (useLibraries != null && useLibraries.length > 0) {
            for (ArrayList<String> library : useLibraries) {
                if (library != null) {
                    Iterator<String> it = library.iterator(); // 使用迭代器遍历，效率会高很多
                    while (it.hasNext()) {
                        String name = it.next();
                        if (!mAllUseLibraries.contains(name)) {
                            mAllUseLibraries.add(name);
                        }
                    }
                }
            }
        }
    }

    public ApplicationInfo doRefit() {
        dealSharedLibraryList();
        dealCpuAbi();
        dealAllDir();
        return mApplicationInfo;
    }

    private void dealSharedLibraryList() {
        // 处理apache
        if (!mAllUseLibraries.contains(ORG_APACHE_HTTP_LEGACY_NAME) && mApplicationInfo.targetSdkVersion < Build.VERSION_CODES.M) {
            mAllUseLibraries.add(ORG_APACHE_HTTP_LEGACY_NAME);
        }

        // 处理无法找到junit.framework.Assert
        if (mAllUseLibraries.contains(ANDROID_TEST_RUNNER_NAME) || (RhBuild.isR() && mApplicationInfo.targetSdkVersion < 30)) {
            mAllUseLibraries.add(ANDROID_TEST_BASE_NAME);
        }

        ArrayList<String> sharedLibraryFiles = new ArrayList<>();
        ArrayList<SharedLibraryInfo> sharedLibraryInfos = new ArrayList<>();
        Iterator<String> iterator = mAllUseLibraries.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            SystemConfigSimple.SharedLibraryEntry entry = SystemConfigSimple.getInstance().getSharedLibrariesEntry(name);
            if (entry == null) {
                if (ORG_APACHE_HTTP_LEGACY_NAME.equals(name)) {
                    // apache这种情况下得自带
                    entry = new SystemConfigSimple.SharedLibraryEntry(name, ORG_APACHE_HTTP_LEGACY_JAR, null);
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

    private void dealCpuAbi() {
        ApplicationInfoRef.primaryCpuAbi.set(mApplicationInfo, mCpuAbi);
    }

    private void dealAllDir() {
        String packageName = mApplicationInfo.packageName;
        String libPath = RhCustomConfig.Helper.getInnerApkLibDir(packageName).getAbsolutePath();
        ApplicationInfoRef.nativeLibraryRootDir.set(mApplicationInfo, libPath);
        ApplicationInfoRef.nativeLibraryDir.set(mApplicationInfo, libPath);
        mApplicationInfo.publicSourceDir = RhCustomConfig.Helper.getInnerApkBaseFile(packageName).getAbsolutePath();
        mApplicationInfo.sourceDir = RhCustomConfig.Helper.getInnerApkBaseFile(packageName).getAbsolutePath();
        if (mIsSplitApk) {
            mApplicationInfo.splitPublicSourceDirs = mSplitCodePaths;
            mApplicationInfo.splitSourceDirs = mSplitCodePaths;
        }
    }

    public static class Builder {
        private RhApplicationInfoRefit mRefit = new RhApplicationInfoRefit();
        public static Builder create() {
            return new Builder();
        }

        public Builder setApplicationInfo(ApplicationInfo applicationInfo) {
            mRefit.setApplicationInfo(applicationInfo);
            return this;
        }

        public Builder setUseLibraries(ArrayList<String>... useLibraries) {
            mRefit.setUseLibraries(useLibraries);
            return this;
        }

        public Builder setCpuAbi(String cpuAbi) {
            mRefit.setCpuAbi(cpuAbi);
            return this;
        }

        public Builder setSplitApk(boolean isSplitApk) {
            mRefit.setSplitApk(isSplitApk);
            return this;
        }

        public Builder setSplitCodePaths(String[] splitCodePaths) {
            mRefit.setSplitCodePaths(splitCodePaths);
            return this;
        }

        public ApplicationInfo doRefit() {
            return mRefit.doRefit();
        }
    }
}
