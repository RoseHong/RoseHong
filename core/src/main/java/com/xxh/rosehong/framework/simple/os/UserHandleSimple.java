package com.xxh.rosehong.framework.simple.os;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/21 19:35
 */

import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.util.SparseArray;

import java.util.Random;

/**
 * 此类为com.android.os.UserHandle的精简版，会有些许修改，因为对于系统环境来说，其只认识
 * host，而内部app因为并没有真正安装在系统里，所以系统对其是陌生的。
 */
public class UserHandleSimple implements Parcelable {

    public static final int PER_USER_RANGE = 100000;
    public static final boolean MU_ENABLED = true;
    public static final SparseArray<UserHandleSimple> sExtraUserHandleCache = new SparseArray<>();

    // 内置特殊用户编号
    public static final int USER_SYSTEM = 0;
    public static final int USER_ALL = -1;
    public static final int USER_CURRENT = -2;
    public static final int USER_CURRENT_OR_SELF = -3;
    public static final int USER_NULL = -10000;

    // 内置特殊用户
    public static final UserHandleSimple SYSTEM = new UserHandleSimple(USER_SYSTEM);
    public static final UserHandleSimple ALL = new UserHandleSimple(USER_ALL);
    public static final UserHandleSimple CURRENT = new UserHandleSimple(USER_CURRENT);
    public static final UserHandleSimple CURRENT_OR_SELF = new UserHandleSimple(USER_CURRENT_OR_SELF);
    public static final UserHandleSimple NULL = new UserHandleSimple(USER_NULL);

    // 预设一些用户
    public static final int MIN_SECONDARY_USER_ID = 10;
    public static final int MAX_EXTRA_USER_HANDLE_CACHE_SIZE = 32;
    private static final int NUM_CACHE_USERS = MU_ENABLED ? 8 : 0;
    private static final UserHandleSimple[] CACHED_USER_HANDLES = new UserHandleSimple[NUM_CACHE_USERS];
    static {
        for (int i=0; i<CACHED_USER_HANDLES.length; i++) {
            CACHED_USER_HANDLES[i] = new UserHandleSimple(MIN_SECONDARY_USER_ID + i);
        }
    }

    final int mHandle;

    public UserHandleSimple(int userId) {
        mHandle = userId;
    }

    public static int getAppId(int uid) {
        return uid % PER_USER_RANGE;
    }

    public static int getUid(int userId, int appId) {
        if (MU_ENABLED) {
            return userId * PER_USER_RANGE + (appId % PER_USER_RANGE);
        } else {
            return appId;
        }
    }

    public int getUid(int appId) {
        return getUid(getIdentifier(), appId);
    }

    public static int getUserId(int uid) {
        if (MU_ENABLED) {
            return uid / PER_USER_RANGE;
        } else {
            return UserHandleSimple.USER_SYSTEM;
        }
    }

    public static int myUserId() {
        // TODO: xxh 这里需要替换成RoseHong给内部app分配的uid，暂时写成当前进程的uid
        return getUserId(Process.myUid());
    }

    public static int hostUserId() {
        // TODO: xxh 这里预留一个获取host的userId的接口，暂时写成当前进程的uid
        return getUserId(Process.myUid());
    }

    public static int getCallingUserId() {
        return getUserId(Binder.getCallingUid());
    }

    public int getIdentifier() {
        return mHandle;
    }

    public static UserHandleSimple getUserHandleForUid(int uid) {
        return of(getUserId(uid));
    }

    public static UserHandleSimple of(int userId) {
        if (userId == USER_SYSTEM) {
            return SYSTEM;
        }
        switch (userId) {
            case USER_ALL:
                return ALL;

            case USER_CURRENT:
                return CURRENT;

            case USER_CURRENT_OR_SELF:
                return CURRENT_OR_SELF;
        }
        if (userId >= MIN_SECONDARY_USER_ID && userId < (MIN_SECONDARY_USER_ID + CACHED_USER_HANDLES.length)) {
            return CACHED_USER_HANDLES[userId - MIN_SECONDARY_USER_ID];
        }
        if (userId == USER_NULL) {
            return NULL;
        }
        return getUserHandleFromExtraCache(userId);
    }

    public static UserHandleSimple getUserHandleFromExtraCache(int userId) {
        synchronized (sExtraUserHandleCache) {
            final UserHandleSimple extraCache = sExtraUserHandleCache.get(userId);
            if (extraCache != null) {
                return extraCache;
            }
            if (sExtraUserHandleCache.size() >= MAX_EXTRA_USER_HANDLE_CACHE_SIZE) {
                sExtraUserHandleCache.removeAt((new Random()).nextInt(MAX_EXTRA_USER_HANDLE_CACHE_SIZE));
            }
            final UserHandleSimple newHandle = new UserHandleSimple(userId);
            sExtraUserHandleCache.put(userId, newHandle);
            return newHandle;
        }
    }

    protected UserHandleSimple(Parcel in) {
        mHandle = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mHandle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserHandleSimple> CREATOR = new Creator<UserHandleSimple>() {
        @Override
        public UserHandleSimple createFromParcel(Parcel in) {
            return new UserHandleSimple(in);
        }

        @Override
        public UserHandleSimple[] newArray(int size) {
            return new UserHandleSimple[size];
        }
    };
}
