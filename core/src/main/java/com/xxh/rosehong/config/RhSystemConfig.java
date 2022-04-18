package com.xxh.rosehong.config;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */

/**
 * 系统级别的配置文件，一般情况下都不需要修改
 */
public class RhSystemConfig {
    /**
     * 初始化服务进程的方法名
     */
    public static final String SERVER_INIT_METHOD = "@";

    /**
     * 通过Bundle.getBinder(KEY_SERVER_CONTROL_BINDER)可以获取服务控制器的binder对象
     */
    public static final String KEY_SERVER_CONTROL_BINDER = "__RH_KEY_SERVER_CONTROL_BINDER__";

    /**
     * 定义服务名称
     */
    public static class RhServiceManagerName {
        public static final String PACKAGE = "package";
    }
}
