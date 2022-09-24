package com.xxh.rosehong.config;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */

import android.content.Context;

import com.xxh.rosehong.client.RhApp;

import java.io.File;

/**
 * 自定义配置文件，开发人员可根据自己的需求作出修改
 */
public class RhCustomConfig {

    /**
     * 服务进程provider的author
     */
    public static final String SERVER_AUTHOR_NAME = "com.xxh.rosehong.client.mgr.RhServiceManager";

    /**
     * 内部apk文件的存放根路径，如：/data/user/0/hostPackageName/inner
     */
    public static final String INNER_APK_ROOT_PATH = RhApp.get().getHostContext().getApplicationInfo().dataDir + "/inner/";

    public static class Helper {

        public static File ensureDir(File dir) {
            if (dir == null) {
                return null;
            }
            if (!dir.exists() && !dir.mkdirs()) {
                return null;
            }
            return dir;
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/
         */
        public static File getInnerApkPackageDir(String packageName) {
            return new File(INNER_APK_ROOT_PATH + packageName + "/");
        }
        public static File ensureInnerApkPackageDir(String packageName) {
            File dir = getInnerApkPackageDir(packageName);
            return ensureDir(dir);
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/lib/
         */
        public static File getInnerApkLibDir(String packageName) {
            return new File(getInnerApkPackageDir(packageName), "/lib");
        }
        public static File ensureInnerApkLibDir(String packageName) {
            File dir = getInnerApkLibDir(packageName);
            return ensureDir(dir);
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/base.apk
         */
        public static File getInnerApkBaseFile(String packageName) {
            return new File(getInnerApkPackageDir(packageName), "base.apk");
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/split_{$splitName}.apk
         */
        public static File getInnerApkSplitFile(String packageName, String splitName) {
            return new File(getInnerApkPackageDir(packageName), String.format("split_%s.apk", splitName));
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/opt/
         */
        public static File getInnerApkOptDir(String packageName) {
            return new File(getInnerApkPackageDir(packageName), "opt/");
        }
        public static File ensureInnerApkOptDir(String packageName) {
            File dir = getInnerApkOptDir(packageName);
            return ensureDir(dir);
        }
    }
}
