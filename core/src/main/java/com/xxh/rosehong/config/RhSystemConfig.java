package com.xxh.rosehong.config;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */

import com.xxh.rosehong.client.RhApp;

/**
 * 系统级别的配置文件，一般情况下都不需要修改
 */
public class RhSystemConfig {
    /**
     * 定义运行的CPU环境
     */
    public static final String MAIN_CPU_ABI = "arm64-v8a";

    /**
     * 初始化服务进程的方法名
     */
    public static final String SERVER_INIT_METHOD = "@";

    /**
     * 通过Bundle.getBinder(KEY_SERVER_CONTROL_BINDER)可以获取服务控制器的binder对象
     */
    public static final String KEY_SERVER_CONTROL_BINDER = "__RH_KEY_SERVER_CONTROL_BINDER__";

    /**
     * 通用成功/失败返回值
     */
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILED = -1;

    /**
     * 定义服务名称
     */
    public static class RhServiceManagerName {
        public static final String PACKAGE = "package";
        public static final String ACTIVITY = "activity";
    }

    /**
     * 定义保存系统数据的文件路径
     */
    public static class RhSystemFile {
        /**
         * 系统数据文件的根路径
         */
        private static final String SYSTEM_FILE_ROOT_PATH = RhApp.get().getHostContext().getApplicationInfo().dataDir + "/system/data/";

        /**
         * 已分配的uid存储文件
         */
        public static final String UID_STORAGE_FILE = SYSTEM_FILE_ROOT_PATH + "uid-setting.db";
    }
}
