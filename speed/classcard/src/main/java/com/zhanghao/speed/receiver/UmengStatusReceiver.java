package com.zhanghao.speed.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;

/**
 * 作者： zhanghao on 2017/8/3.
 * 功能：${des}
 */

public class UmengStatusReceiver extends BroadcastReceiver {
    public static final String ISADDALIAS = "isAddAlias";
    public static final String action = "com.classCard.UmengStatusReceiver";
    public static final String ALIAS = "alias";
    public static String ENABLE = "enable";
    private static boolean enable = false;
    private String alias;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isAddAlias = intent.getBooleanExtra(ISADDALIAS, false);

        enable = intent.getBooleanExtra(ENABLE, false);
        final PushAgent mPushAgent = PushAgent.getInstance(context);
        if (isAddAlias && enable) {
            try {
                alias = "userId";
                //设置用户别名
                mPushAgent.addExclusiveAlias(alias, "LeeoneAlias", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {

                        //设置用户标签
                        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
                            @Override
                            public void onMessage(boolean b, ITagManager.Result result) {

                            }
                        }, "classCard");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (!isAddAlias) {
            if (alias == null) return;
            mPushAgent.removeAlias(alias, "LeeoneAlias", new UTrack.ICallBack() {
                @Override
                public void onMessage(boolean b, String s) {

                }
            });
        }
    }
}
