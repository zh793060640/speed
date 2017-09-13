package com.leeone.classcard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 作者： zhanghao on 2017/8/11.
 * 功能：${des}
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BootBroadcastReceiver";

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Boot this system , BootBroadcastReceiver onReceive()");

        if (intent.getAction().equals(ACTION_BOOT)) {
            Log.i(TAG, "BootBroadcastReceiver onReceive(), Do thing!");
            // 后边的XXX.class就是要启动的服务
//            Intent actIntent = new Intent(context.getApplicationContext(), MainActivity.class);
//            actIntent.setAction("android.intent.action.MAIN");
//            actIntent.addCategory("android.intent.category.LAUNCHER");
//            actIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(actIntent);
            Intent actIntent = context.getPackageManager().getLaunchIntentForPackage("com.leeone.classcard");
            context.startActivity(actIntent);
        }
    }
}
