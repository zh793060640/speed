package com.tencent.qcloud.timchat.utils;

import android.util.Log;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;

/**
 * 作者： zhanghao on 2017/12/5.
 * 功能：${des}
 */

public class FriendShipUtils {

    private static String tag = "FriendShipUtils";

    public static void modifyHeadImage(String faceUrl) {
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        param.setFaceUrl(faceUrl);
        modifyProfile(param);
    }

    public static void modifyNickName(String nickName) {
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        param.setNickname(nickName);
        modifyProfile(param);
    }

    private static void modifyProfile(TIMFriendshipManager.ModifyUserProfileParam param) {
        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(tag, "modifyProfile failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess() {
                Log.e(tag, "modifyProfile succ");
            }
        });
    }
}
