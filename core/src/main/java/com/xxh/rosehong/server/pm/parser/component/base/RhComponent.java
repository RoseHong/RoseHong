package com.xxh.rosehong.server.pm.parser.component.base;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/7/4 23:40
 */

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Parcel;

import com.xxh.rosehong.framework.ref.android.content.pm.PackageParserRef.*;
import com.xxh.rosehong.server.pm.parser.RhPackage;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * 此类对应着PackageParser$Component
 */
public class RhComponent<II extends RhIntentInfo> {

    public RhPackage owner;

    public ArrayList<II> intents;
    public String className;
    public Bundle metaData;
    public int order;
    public ComponentName componentName;
    public String componentShortName;

    public RhComponent(Class<II> _IIClass, Parcel in) {
        try {
            int N = in.readInt();
            intents = new ArrayList<>(N);
            //这里需要通过II的构造方法，把II给解析出来
            Constructor<II> constructor = _IIClass.getConstructor(Parcel.class);
            while (N-- > 0) {
                intents.add(constructor.newInstance(in));
            }
            className = in.readString();
            metaData = in.readBundle();
            order = in.readInt();
            componentName = in.readParcelable(_IIClass.getClassLoader());
            componentShortName = in.readString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 用于安装器解析
    public RhComponent(Class<II> _IIClass, Object baseComponent) {
        try {
            if (intents == null) {
                intents = new ArrayList<>();
            }
            // 解析IntentInfo
            ArrayList<Object> baseIntents = ComponentRef.intents.get(baseComponent);
            for (Object item : baseIntents) {
                // 这里我们通过_IIClass来对相应的II进行实例化
                Constructor<II> constructor = _IIClass.getConstructor(Object.class);
                II intentInfo = constructor.newInstance(item);
                intents.add(intentInfo);
            }
            className = ComponentRef.className.get(baseComponent);
            metaData = ComponentRef.metaData.get(baseComponent);
            order = ComponentRef.order.get(baseComponent);
            componentName = ComponentRef.componentName.get(baseComponent);
            componentShortName = ComponentRef.componentShortName.get(baseComponent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(intents != null ? intents.size() : 0);
        if (intents != null) {
            for (II info : intents) {
                info.writeToParcel(dest, flags);
            }
        }
        dest.writeString(className);
        dest.writeBundle(metaData);
        dest.writeInt(order);
        dest.writeParcelable(componentName, flags);
        dest.writeString(componentShortName);
    }

    public ComponentName getComponentName() {
        if (componentName != null) {
            return componentName;
        }
        if (className != null) {
            componentName = new ComponentName(owner.applicationInfo.packageName, className);
        }
        return componentName;
    }
}
