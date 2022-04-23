package com.xxh.rosehong.utils.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/23 22:40
 */
public class RhFile {
    private static final String TAG = RhFile.class.getSimpleName();

    public static void copyFile(String srcPath, String destPath) {
        copyFile(new File(srcPath), new File(destPath));
    }
    public static void copyFile(File srcFile, File destFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(srcFile);
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fileInputStream.read(bytes)) > 0) {
                fileOutputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
