package com.xxh.rosehong.config;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhConfig {
    /**
     * 服务进程provider的author
     */
    public static final String SERVER_AUTHOR_NAME;
    /**
     * 初始化服务进程的方法名
     */
    public static final String SERVER_INIT_METHOD;

    static {
        SERVER_INIT_METHOD = "@";
        SERVER_AUTHOR_NAME = "com.xxh.rosehong.client.RhServiceManager";
    }
}
