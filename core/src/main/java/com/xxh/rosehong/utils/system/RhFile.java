package com.xxh.rosehong.utils.system;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/23 22:40
 */
public class RhFile {
    private static final String TAG = RhFile.class.getSimpleName();
    public static String APK = ".apk";
    public static String APKS = ".apks";
    public static String XAPK = ".xapk";

    public static void copyStream(InputStream srcStream, OutputStream destStream) throws IOException {
        byte[] buf = new byte[1024 * 1024];
        int len;
        while ((len = srcStream.read(buf)) > 0) {
            destStream.write(buf, 0, len);
        }
    }

    public static void copyFile(String srcPath, String destPath) throws IOException {
        copyFile(new File(srcPath), new File(destPath));
    }
    public static void copyFile(File srcFile, File destFile) throws IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(srcFile);
            fileOutputStream = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fileInputStream.read(bytes)) > 0) {
                fileOutputStream.write(bytes, 0, len);
            }
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isApkFile(File file) {
        return isApkFile(file.getName());
    }

    public static boolean isApkFile(String filePath) {
        if (!TextUtils.isEmpty(filePath) && filePath.endsWith(APK)) {
            return true;
        }
        return false;
    }

    public static boolean isApksFile(String filePath) {
        if (!TextUtils.isEmpty(filePath) && (filePath.endsWith(APKS) || filePath.endsWith(XAPK))) {
            return true;
        }
        return false;
    }

    public static String getFileNameFromPath(String path) {
        int last = path.lastIndexOf("/");
        if (last == -1) {
            return path;
        }
        return path.substring(last + 1);
    }

    public static void deleteDeep(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (!file.isFile()) {
            for (File subFile : file.listFiles()) {
                deleteDeep(subFile);
            }
        }
        file.delete();
    }
}
