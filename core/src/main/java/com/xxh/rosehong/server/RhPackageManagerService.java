package com.xxh.rosehong.server;

import com.xxh.rosehong.bridge.IRhPackageManager;
import com.xxh.rosehong.utils.system.RhLog;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class RhPackageManagerService extends IRhPackageManager.Stub {
    private static final String TAG = RhPackageManagerService.class.getSimpleName();

    @Override
    public void test() {
        RhLog.i(TAG, "test!!!!!!!!!!!!");
    }
}
