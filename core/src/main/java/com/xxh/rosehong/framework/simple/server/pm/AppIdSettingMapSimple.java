package com.xxh.rosehong.framework.simple.server.pm;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/23 23:34
 */

import android.os.Process;
import android.util.ArrayMap;

import com.xxh.rosehong.config.RhSystemConfig;
import com.xxh.rosehong.utils.storage.RhStorageUtils;
import com.xxh.rosehong.utils.system.RhLog;

import java.io.File;
import java.util.ArrayList;

/**
 * 此类为com.android.server.pm.AppIdSettingMap的精简且修改版，主要用于分配和处理appId
 */
public class AppIdSettingMapSimple {
    private static final String TAG = AppIdSettingMapSimple.class.getSimpleName();

    private static AppIdSettingMapSimple sInstance = null;

    private int mFirstAvailableAppId = Process.FIRST_APPLICATION_UID;
    private ArrayMap<String, SharedUserSetting> mSharedUsers = new ArrayMap<>();
    private ArrayList<SharedUserSetting> mNonSystemSettings = new ArrayList<>();
    private ArrayList<SharedUserSetting> mSystemSettings = new ArrayList<>();
    private File mStorageFile = new File(RhSystemConfig.RhSystemFile.APP_ID_STORAGE_FILE);

    public static AppIdSettingMapSimple get() {
        if (sInstance == null) {
            sInstance = new AppIdSettingMapSimple();
            // TODO: 从file/db加载，并调用registerExistingAppId进行注册
        }
        return sInstance;
    }

    private AppIdSettingMapSimple() {
        loadDataStorage();
    }

    private void loadDataStorage() {
        RhStorageUtils.load(mStorageFile, parcel -> {
            if (!mNonSystemSettings.isEmpty()) {
                mNonSystemSettings.clear();
                int size = parcel.readInt();
                for (int i=0; i<size; i++) {
                    int appId = parcel.readInt();
                    SharedUserSetting setting = new SharedUserSetting();
                    setting.mAppId = appId;
                    setFirstAvailableAppId(appId);
                    // TODO: 下面这个方法是直接抄FrameWork的，有点复杂了，得进行精简
                    registerExistingAppId(appId, setting);
                }
            }
        });
    }

    private void saveDataStorage() {
        RhStorageUtils.save(mStorageFile, parcel -> {
            parcel.writeInt(mNonSystemSettings.size());
            for (SharedUserSetting setting : mNonSystemSettings) {
                parcel.writeInt(setting.mAppId);
            }
        });
    }

    private boolean registerExistingAppId(int appId, SharedUserSetting setting) {
        if (appId > Process.FIRST_APPLICATION_UID) {
            int size = mNonSystemSettings.size();
            final int index = appId - Process.FIRST_APPLICATION_UID;
            while (index >= size) {
                size++;
            }
            if (mNonSystemSettings.get(index) != null) {
                RhLog.w(TAG, "Adding duplicate app id: " + appId);
                return false;
            }
            mNonSystemSettings.set(index, setting);
        } else {
            if (mSystemSettings.get(appId) != null) {
                RhLog.w(TAG, "Adding duplicate app id: " + appId);
                return false;
            }
            mSystemSettings.set(appId, setting);
        }
        return true;
    }

    public int getAppId(String sharedUserId) {
        SharedUserSetting s = mSharedUsers.get(sharedUserId);
        if (s == null) {
            s = new SharedUserSetting();
            s.mAppId = acquireAndRegisterNewAppId(s);
            if (s.mAppId < 0) {
                RhLog.printStackTrace(TAG, new Exception("Creating appId " + sharedUserId + "failed."));
                return -1;
            }
            RhLog.i(TAG, "New shared user" + sharedUserId + ": id=" + s.mAppId);
            saveDataStorage();
        }
        return s.mAppId;
    }

    private int acquireAndRegisterNewAppId(SharedUserSetting obj) {
        final int size = mNonSystemSettings.size();
        for (int i = mFirstAvailableAppId - Process.FIRST_APPLICATION_UID; i < size; i++) {
            if (mNonSystemSettings.get(i) == null) {
                mNonSystemSettings.set(i, obj);
                return Process.FIRST_APPLICATION_UID + i;
            }
        }

        if (size > (Process.LAST_APPLICATION_UID - Process.FIRST_APPLICATION_UID)) {
            return -1;
        }

        mNonSystemSettings.add(obj);
        return Process.FIRST_APPLICATION_UID + size;
    }

    private void setFirstAvailableAppId(int uid) {
        if (uid > mFirstAvailableAppId) {
            mFirstAvailableAppId = uid;
        }
    }

    private class SharedUserSetting {
        public int mAppId;
    }
}
