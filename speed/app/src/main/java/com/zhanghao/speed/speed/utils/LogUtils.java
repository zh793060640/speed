package com.zhanghao.speed.speed.utils;

import android.util.Log;

import com.zhanghao.speed.speed.AppConfig;


/**
 * Created by PC on 2017/3/6.
 * 作者 ：张浩
 * 作用：
 */

public class LogUtils {

    public static void Le(String msg) {
        if (AppConfig.LOGABLE) {
            Log.e(AppConfig.LOG_TAG, "error-->" + msg);
        }
    }

    public static void Le(String tag, String msg) {
        if (AppConfig.LOGABLE) {
            Log.e(tag, msg);
        }
    }

    public static void Ld(String msg) {
        if (AppConfig.LOGABLE) {
            Log.d(AppConfig.LOG_TAG, msg);
        }
    }

    public static void Ld(String tag, String msg) {
        if (AppConfig.LOGABLE) {
            Log.d(tag, msg);
        }
    }

    public static void Li(String msg) {
        if (AppConfig.LOGABLE) {
            Log.i(AppConfig.LOG_TAG, msg);
        }
    }

    public static void Li(String tag, String msg) {
        if (AppConfig.LOGABLE) {
            Log.i(tag, msg);
        }
    }


    public static void So(String msg) {
        if (AppConfig.LOGABLE) {
            System.out.println(msg);
        }
    }

    public static void Se(String msg) {
        if (AppConfig.LOGABLE) {
            System.err.println(msg);
        }
    }

    public static void EPrint(Exception e) {
        if (AppConfig.LOGABLE) {
            Le(e.getMessage());
            e.printStackTrace();
        }
    }

}
