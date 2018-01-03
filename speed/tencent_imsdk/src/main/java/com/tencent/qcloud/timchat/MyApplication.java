package com.tencent.qcloud.timchat;

import android.content.Context;

import com.leeone.leeonecore.util.AppManager;


/**
 * 全局Application
 */
public class MyApplication  {

    private static Context context;


    public static Context getContext() {
        return AppManager.I().getApplicationContext();
    }



}
