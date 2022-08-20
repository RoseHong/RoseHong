package com.xxh.rosehong.utils.system;

import dalvik.system.DexClassLoader;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/18 22:30
 */
public class RhDexOptimizer {
    private static final String TAG = RhDexOptimizer.class.getSimpleName();

    public static void optimizeDex(String dexFilePath, String optFilePath, String libFilePath){
        try {
            if (!RhBuild.isQ()){
                new DexClassLoader(dexFilePath, optFilePath, libFilePath, RhDexOptimizer.class.getClassLoader());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
