package com.leeone.classcard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.leeone.core.utils.LogUtils;

/**
 * 作者： zhanghao on 2017/8/14.
 * 功能：${des}
 */

public class InnerRecevier extends BroadcastReceiver {

    final String SYSTEM_DIALOG_REASON_KEY = "reason";

    final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

    final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    LogUtils.e("SYSTEM_DIALOG_REASON_HOME_KEY");
                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    LogUtils.e("SYSTEM_DIALOG_REASON_RECENT_APPS");
                }
            }
        }
    }
}
