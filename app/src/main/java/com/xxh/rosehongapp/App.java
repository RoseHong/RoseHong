package com.xxh.rosehongapp;

import android.app.Application;

import com.xxh.rosehong.utils.system.RhLog;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:45
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RhLog.openLog();
    }
}
