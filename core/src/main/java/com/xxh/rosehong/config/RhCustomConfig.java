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
         * @param packageName 包名
         * @return 路径
         */
        public static File getInnerApkBasePathByPackageName(String packageName) {
            return new File(INNER_APK_ROOT_PATH + packageName + "/");
        }
        public static File ensureInnerApkBasePathByPackageName(String packageName) {
            File dir = getInnerApkBasePathByPackageName(packageName);
            if (dir == null || !dir.isDirectory()) {
                return null;
            }
            if (!dir.exists() && !dir.mkdirs()) {
                return null;
            }
            return dir;
        }
        public static File ensureInnerApkFileByPackageName(String packageName, String file) {
            File dir = ensureInnerApkBasePathByPackageName(packageName);
            return ensureFile(new File(dir, file));
        }
    }
}
