package com.xxh.rosehong.config;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */

import android.os.Environment;

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

    public static String getInnerApkPathByPackageName(String packageName) {
        return INNER_APK_ROOT_PATH + packageName + "/";
    }
}
