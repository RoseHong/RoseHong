package com.xxh.rosehong.server.pm;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/9/4 23:44
 */

import android.os.Process;
import android.util.ArrayMap;

import com.xxh.rosehong.config.RhSystemConfig;
import com.xxh.rosehong.utils.storage.RhStorageUtils;
import com.xxh.rosehong.utils.system.RhLog;

import java.io.File;

/**
 * 此类为com.android.server.pm.AppIdSettingMap的修改版，在FrameWork中虽说是分配appId，实则其为uid，在RoseHong
 * 系统里，此类亦为分配一个uid，此uid由RoseHong管理，其意义与FrameWork的uid一样。
 */
public class RhUidSettingMap {
    private static final String TAG = RhUidSettingMap.class.getSimpleName();
    private static RhUidSettingMap sInstance = null;

    /**
     * 记录当前能够使用的uid编号，其值会一直累加
     */
    private int mFirstAvailableUid = Process.FIRST_APPLICATION_UID;
    /**
     * 记录key-value为sharedUser-SharedUserSetting的map，相同sharedUser,其uid也相同
     */
    private ArrayMap<String, SharedUserSetting> mSharedUsers = new ArrayMap<>();

    private File mStorageFile = new File(RhSystemConfig.RhSystemFile.UID_STORAGE_FILE);

    public static RhUidSettingMap get() {
        if (sInstance == null) {
            sInstance = new RhUidSettingMap();
        }
        return sInstance;
    }

    private RhUidSettingMap() {
        loadUidFromStorage();
    }

    private void loadUidFromStorage() {
        RhStorageUtils.load(mStorageFile, parcel -> {
            if (!mSharedUsers.isEmpty()) {
                mSharedUsers.clear();
            }

            int N = parcel.readInt();
            for (int i=0; i<N; i++) {
                String sharedUserId = parcel.readString();
                int uid = parcel.readInt();
                SharedUserSetting setting = createSharedUserSetting(sharedUserId, uid);
                mSharedUsers.put(sharedUserId, setting);
                setFirstAvailableAppId(uid);
            }
        });
    }

    private void saveUidToStorage() {
        RhStorageUtils.save(mStorageFile, parcel -> {
            parcel.writeInt(mSharedUsers.size());
            for (String key : mSharedUsers.keySet()) {
                SharedUserSetting setting = mSharedUsers.get(key);
                parcel.writeString(setting.mSharedUserId);
                parcel.writeInt(setting.mUid);
            }
        });
    }

    private SharedUserSetting createSharedUserSetting(String sharedUserId, int uid) {
        SharedUserSetting setting = new SharedUserSetting();
        setting.mSharedUserId = sharedUserId;
        setting.mUid = uid;
        return setting;
    }

    public int getUid(String sharedUserId) {
        SharedUserSetting s = mSharedUsers.get(sharedUserId);
        if (s == null) {
            s = createSharedUserSetting(sharedUserId, acquireAndRegisterNewUid());
            if (s.mUid < 0) {
                RhLog.printStackTrace(TAG, new Exception("Creating uid " + sharedUserId + "failed."));
                return -1;
            }
            RhLog.i(TAG, "New shared user" + sharedUserId + ": uid=" + s.mUid);
            mSharedUsers.put(sharedUserId, s);
            saveUidToStorage();
        }
        RhLog.i(TAG, "Get shared user" + sharedUserId + ": uid=" + s.mUid);
        return s.mUid;
    }

    private int acquireAndRegisterNewUid() {
        // 下面代码主要用于修正mFirstAvailableUid
        for (SharedUserSetting s : mSharedUsers.values()) {
            setFirstAvailableAppId(s.mUid);
        }
        return ++mFirstAvailableUid;
    }

    private void setFirstAvailableAppId(int uid) {
        if (uid > mFirstAvailableUid) {
            mFirstAvailableUid = uid;
        }
    }

    private class SharedUserSetting {
        public String mSharedUserId;
        public int mUid;
    }
}
