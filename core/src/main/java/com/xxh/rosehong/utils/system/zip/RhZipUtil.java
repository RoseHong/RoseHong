package com.xxh.rosehong.utils.system.zip;

import com.xxh.rosehong.utils.system.RhFile;
import com.xxh.rosehong.utils.system.RhLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/6/21 22:58
 */
public class RhZipUtil {
    private static final String TAG = RhZipUtil.class.getSimpleName();
    private static List<String> SUPPORT_FILE_EXT = new ArrayList<>();

    static {
        SUPPORT_FILE_EXT.add(".zip");
        SUPPORT_FILE_EXT.add(".rar");
        SUPPORT_FILE_EXT.add(".7z");
        SUPPORT_FILE_EXT.add(".apk");
        SUPPORT_FILE_EXT.add(".xapk");
        SUPPORT_FILE_EXT.add(".apks");
    }

    private static boolean isSupportFile(File surFile) {
        for (String ext : SUPPORT_FILE_EXT) {
            if (surFile.getName().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param surFile 需要解压的文件
     * @param outFile 解压到哪里
     * @return 解压后的文件夹路径
     */
    public static File extractZipFile(File surFile, File outFile) {
        if (surFile == null || !surFile.isFile() || outFile == null || !outFile.isDirectory()) {
            return null;
        }
        if (!isSupportFile(surFile)) {
            RhLog.w(TAG, "UnSupport File: " + surFile.getAbsolutePath());
            return null;
        }
        try {
            if (!outFile.exists()) {
                outFile.mkdirs();
            }
            ZipFile zipFile = new ZipFile(surFile);
            Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
            while (zipEntries.hasMoreElements()) {
                ZipEntry zipEntry = zipEntries.nextElement();
                if (zipEntry.isDirectory()) {
                    //TODO: 暂不处理
                    continue;
                }
                String zipEntryFileName = RhFile.getFileNameFromPath(zipEntry.getName());
                File extractFile = new File(outFile, zipEntryFileName);
                extractFile.deleteOnExit();
                try (InputStream in = zipFile.getInputStream(zipEntry); OutputStream out = new FileOutputStream(extractFile)) {
                    RhFile.copyStream(in, out);
                }
            }
            zipFile.close();
            return outFile;
        } catch (Exception e) {
            RhLog.printStackTrace(TAG, e);
        }
        return null;
    }
}
