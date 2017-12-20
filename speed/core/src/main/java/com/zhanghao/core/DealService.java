package com.zhanghao.core;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * 作者： zhanghao on 2017/11/23.
 * 功能：${des}
 */

public class DealService extends IntentService {

    private static final String ACTION_UPLOAD_IMG = "intentservice.action.UPLOAD_IMAGE";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public static void startDealService(Context context) {
        Intent intent = new Intent(context, DealService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        context.startService(intent);
    }

    public DealService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
