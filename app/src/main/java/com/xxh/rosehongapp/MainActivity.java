package com.xxh.rosehongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xxh.rosehong.utils.ref.RhClass;
import com.xxh.rosehong.utils.ref.RhField;
import com.xxh.rosehong.utils.ref.RhReflection;
import com.xxh.rosehong.utils.ref.RhStaticField;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bypassHiddenApiOnClick(View view) {
        RhReflection.bypassHiddenApi();

        /*try {
            Object mLoadedApk = Application.class.getField("mLoadedApk");
            if (mLoadedApk != null) {
                Log.w(MainActivity.class.getSimpleName(), "mLoadedApk: " + mLoadedApk.getClass().getSimpleName());
            } else {
                Log.e(MainActivity.class.getSimpleName(), "mLoadedApk reflection fail!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static class ApplicationRef {
        public static Class REF = RhClass.init(ApplicationRef.class, Application.class);
        public static RhField<Object> mLoadedApk;
        public static RhStaticField<String> TAG;
    }
}