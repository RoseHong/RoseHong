package com.xxh.rosehong.utils.system.zip;

import com.xxh.rosehong.utils.system.RhFile;

import java.util.zip.ZipEntry;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/5/24 23:37
 */
public class RhZipEntry {
    private static final String TAG = RhZipEntry.class.getSimpleName();

    private ZipEntry mZipEntry;
    private String mFileName;
    private String mFilePath;

    public RhZipEntry(ZipEntry zipEntry) {
        mZipEntry = zipEntry;
        mFileName = RhFile.getFileNameFromPath(zipEntry.getName());
        mFilePath = zipEntry.getName();
    }

    public String getFileName() {
        return mFileName;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public boolean isDir() {
        return mFileName.endsWith("/");
    }
}
