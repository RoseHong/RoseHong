package com.xxh.rosehongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.xxh.rosehong.utils.hooks.proxy.RhDynamicProxy;
import com.xxh.rosehong.utils.hooks.proxy.RhDynamicProxyMethod;
import com.xxh.rosehong.utils.hooks.proxy.RhDynamicProxyMethodParams;
import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhField;
import com.xxh.rosehong.utils.ref.RhReflection;
import com.xxh.rosehong.utils.ref.RhStaticField;
import com.xxh.rosehongapp.howprovider.HowBinderClient;
import com.xxh.rosehongapp.howprovider.HowProviderClient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testManagerServiceOnClick(View view) {
        TestManagerService.test();
    }

    private interface ITestDynamicProxy {
        int doTest();
    }

    private static class TestDynamic implements ITestDynamicProxy {

        @Override
        public int doTest() {
            int a = 1;
            int b = 0;
            int s = a / b;
            return s;
        }
    }

    public void dynamicProxyOnClick(View view) {
        TestDynamic testDynamic = new TestDynamic();
        try {
            RhDynamicProxy<ITestDynamicProxy> rhDynamicProxy = new RhDynamicProxy<>(testDynamic);
            rhDynamicProxy.addDynamicProxyMethod(new RhDynamicProxyMethod("doTest") {
                @Override
                public Object callHookedMethod(RhDynamicProxyMethodParams params) throws InvocationTargetException, IllegalAccessException {
                    return super.callHookedMethod(params);
                }
            });
            ITestDynamicProxy proxyTestDynamic = rhDynamicProxy.getProxyObject();
            proxyTestDynamic.doTest();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void bypassHiddenApiOnClick(View view) {
        RhReflection.bypassHiddenApi();

        /*try {
            Object mLoadedApk = Application.class.getDeclaredField("mLoadedApk");
            if (mLoadedApk != null) {
                Log.w(MainActivity.class.getSimpleName(), "mLoadedApk: " + mLoadedApk.getClass().getSimpleName());
            } else {
                Log.e(MainActivity.class.getSimpleName(), "mLoadedApk reflection fail!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Log.e(MainActivity.class.getSimpleName(), "ApplicationRef: " + ApplicationRef.REF);
        Log.e(MainActivity.class.getSimpleName(), "mLoadedApk: " + ApplicationRef.mLoadedApk.toString());
        Log.e(MainActivity.class.getSimpleName(), "TAG: " + ApplicationRef.TAG.get());
    }

    public static class ApplicationRef {
        public static Class REF = RhClass.init(ApplicationRef.class, Application.class);
        public static RhField<Object> mLoadedApk;
        public static RhStaticField<String> TAG;
    }

    private IHowBinder iHowBinder = null;
    public void binderServiceOnClick(View view) {
        /**
         * 参考android framework的设计，采用C/S架构来做实现则显得非常有必须要，
         * 毕竟运行在虚拟环境下的app，没有必要都跑着一个具有同样功能的service。
         * */
        if (iHowBinder == null) {
            Intent intent = new Intent(this, HowService.class);
            bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    iHowBinder = IHowBinder.Stub.asInterface(service);
                    Log.e("===========", "onServiceConnected!!!!===" + name);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    iHowBinder = null;
                }
            }, Context.BIND_AUTO_CREATE);
        } else {
            try {
                iHowBinder.callRemote(1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void binderProviderOnClick(View view) {
        HowProviderClient.getInstance().init(this);
        int res = HowBinderClient.getsInstance().callRemote(88);
        Log.e(MainActivity.class.getSimpleName(), "binderProviderOnClick: " + res);
    }
}