package com.xxh.rosehong.client.mgr;

import com.xxh.rosehong.bridge.IRhActivityManager;
import com.xxh.rosehong.config.RhSystemConfig;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/18 下午11:17
 */
public class RhActivityManager extends RhManagerBase<IRhActivityManager>{
    private static final String TAG = RhActivityManager.class.getSimpleName();

    private static RhActivityManager sInstance = new RhActivityManager();

    public RhActivityManager() {
        super(IRhActivityManager.class, RhSystemConfig.RhServiceManagerName.ACTIVITY);
    }

    public static RhActivityManager get() {
        return sInstance;
    }
}
