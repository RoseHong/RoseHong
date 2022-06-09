package com.xxh.rosehong.utils.system.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/5/24 23:27
 */
public class RhZipFile {
    private static final String TAG = RhZipFile.class.getSimpleName();

    private String mFileName;
    private ZipFile mZipFile;
    private List<RhZipEntry> mEntries;

    public RhZipFile(File zipFile) throws IOException {
        mZipFile = new ZipFile(zipFile);
        mFileName = zipFile.getName();
    }

    public List<RhZipEntry> listEntries() {
        if (mZipFile == null) {
            return Collections.emptyList();
        }
        if (mEntries != null && mEntries.size() > 0) {
            return mEntries;
        }
        mEntries = new ArrayList<>();
        Enumeration<? extends ZipEntry> zipEntries = mZipFile.entries();
        while (zipEntries.hasMoreElements()) {
            mEntries.add(new RhZipEntry(zipEntries.nextElement()));
        }
        return mEntries;
    }
}
