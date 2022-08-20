package com.xxh.rosehong.config;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */

import android.os.Environment;

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
     * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner
     */
    public static final String INNER_APK_ROOT_PATH = Environment.getDataDirectory().getPath() + "/inner/";

    public static class Helper {

        public static File ensureFile(File file) {
            if (file == null) {
                return null;
            }
            if (!file.exists() && !file.mkdirs()) {
                return null;
            }
            return file;
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/
         */
        public static File getInnerApkPackageDir(String packageName) {
            return new File(INNER_APK_ROOT_PATH + packageName + "/");
        }
        public static File ensureInnerApkPackageDir(String packageName) {
            File dir = getInnerApkPackageDir(packageName);
            return ensureFile(dir);
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/lib/
         */
        public static File getInnerApkLibDir(String packageName) {
            return new File(getInnerApkPackageDir(packageName), "/lib");
        }
        public static File ensureInnerApkLibDir(String packageName) {
            File dir = getInnerApkLibDir(packageName);
            return ensureFile(dir);
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/base.apk
         */
        public static File getInnerApkBaseFile(String packageName) {
            return new File(getInnerApkPackageDir(packageName), "base.apk");
        }
        public static File ensureInnerApkBaseFile(String packageName) {
            File file = getInnerApkBaseFile(packageName);
            return ensureFile(file);
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/split_{$splitName}.apk
         */
        public static File getInnerApkSplitFile(String packageName, String splitName) {
            return new File(getInnerApkPackageDir(packageName), String.format("split_%s.apk", splitName));
        }
        public static File ensureInnerApkSplitFile(String packageName, String splitName) {
            File file = getInnerApkSplitFile(packageName, splitName);
            return ensureFile(file);
        }

        /**
         * 内部apk文件的存放根路径，如：/data/data/hostPackageName/inner/packageName/opt/
         */
        public static File getInnerApkOptDir(String packageName) {
            return new File(getInnerApkPackageDir(packageName), "opt/");
        }
        public static File ensureInnerApkOptDir(String packageName) {
            File dir = getInnerApkOptDir(packageName);
            return ensureFile(dir);
        }
    }
}
