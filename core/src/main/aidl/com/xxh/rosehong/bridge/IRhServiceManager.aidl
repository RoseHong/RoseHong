/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/3/28 下午10:01
 */
package com.xxh.rosehong.bridge;

interface IRhServiceManager {
    IBinder getService(String serviceName);
    void register(String serviceName, IBinder service);
    IBinder unregister(String serviceName);
}