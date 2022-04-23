/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/3/28 下午10:01
 */

package com.xxh.rosehong.bridge;

import com.xxh.rosehong.model.RhInstallResMod;

interface IRhPackageManager {
    RhInstallResMod installApk(String apkPath);
}