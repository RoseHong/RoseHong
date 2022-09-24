package com.xxh.rosehong.utils.storage;

import android.os.Parcel;

import com.xxh.rosehong.utils.system.RhChmodMode;
import com.xxh.rosehong.utils.system.RhFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/24 22:47
 */
public class RhStorageUtils {
    private static final String TAG = RhStorageUtils.class.getSimpleName();


    public interface IRhDataStorage {
        void run(Parcel parcel);
    }

    public static void save(File file, IRhDataStorage dataStorageMtd) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
                RhFile.chmod(parentFile, RhChmodMode.MODE_777);
            }
        }
        Parcel parcel = Parcel.obtain();
        try {
            dataStorageMtd.run(parcel);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(parcel.marshall());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parcel.recycle();
        }
    }

    public static void load(File file, IRhDataStorage dataStorage) {
        if (file == null || !file.isFile() || !file.exists()) {
            return;
        }

        Parcel parcel = Parcel.obtain();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = RhFile.readFromInputStream(fileInputStream);
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);
            dataStorage.run(parcel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parcel.recycle();
        }
    }
}
