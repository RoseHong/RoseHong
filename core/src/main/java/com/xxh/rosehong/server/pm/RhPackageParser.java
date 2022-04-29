package com.xxh.rosehong.server.pm;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/27 23:39
 */

import java.io.File;

/**
 * 此类对应着android.content.pm.PackageParser，换句话讲就是，此类对apk的解析基本上是参考PackageParser来实现的，
 * 同时有些实现就是直接调用PackageParser里的方法。
 */
public class RhPackageParser {
    private static final String TAG = RhPackageParser.class.getSimpleName();

    public RhPackage parsePackage(File packageFile) {
        return null;
    }
}
