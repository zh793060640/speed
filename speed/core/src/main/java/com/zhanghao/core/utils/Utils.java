package com.zhanghao.core.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
    public static boolean isAppRunningForeground(Context var0) {
        ActivityManager var1 = (ActivityManager)var0.getSystemService("activity");

        try {
            List var2 = var1.getRunningTasks(1);
            if(var2 != null && var2.size() >= 1) {
                boolean var3 = var0.getPackageName().equalsIgnoreCase(((ActivityManager.RunningTaskInfo)var2.get(0)).baseActivity.getPackageName());
                return var3;
            } else {
                return false;
            }
        } catch (SecurityException var4) {
            var4.printStackTrace();
            return false;
        }
    }

}