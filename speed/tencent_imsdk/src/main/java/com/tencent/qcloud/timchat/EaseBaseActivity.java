/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.qcloud.timchat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.leeone.leeonecore.util.AppManager;
import com.leeone.leeonecore.util.CheckDataUtils;
import com.leeone.leeonecore.util.Config;
import com.leeone.leeonecore.util.LSUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.MultiImageSelectorNoUploadActivity;
import rx.Subscription;

import static com.leeone.leeonecore.constant.Constance.REQUEST_IMAGE;

public class EaseBaseActivity extends AppCompatActivity {

    protected AppCompatActivity activity = this;
    protected Intent extraIntent;
    protected static String TAG;
    private ActivityManager activityManager;
    protected ArrayList<Subscription> subscriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        TAG = this.getClass().getSimpleName();
        Log.i(TAG, "onCreate: ");
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        extraIntent = getIntent();
        //理论上应该放在launcher activity,放在基类中所有集成此库的app都可以避免此问题
        if (!isTaskRoot()) {
            String action = extraIntent.getAction();
            if (extraIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        AppManager.I().addActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.LOGABLE) {
            LSUtil.Ld("LifecycleLeeOne", getClass().getSimpleName() + "-->ActivityLifeCycle" + "-->onResume");
        }
        // onresume时，取消notification显示
//        if (EaseUI.getInstance() != null && EaseUI.getInstance().getNotifier() != null) {
//            EaseUI.getInstance().getNotifier().reset();
//            EaseUI.getInstance().pushActivity(this);
//        }
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Config.LOGABLE) {
            LSUtil.Ld("LifecycleLeeOne", getClass().getSimpleName() + "-->ActivityLifeCycle" + "-->onPause");
        }

       // EaseUI.getInstance().popActivity(this);
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    /**
     *隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                AppManager.IMM.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void showSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 回调接口
     *
     * @author zhaoxin5
     */
    public interface MyTouchListener {
        public void onTouchEvent(MotionEvent event);
    }

    /*
     * 保存MyTouchListener接口的列表
     */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        try {
            return getWindow().superDispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            LSUtil.EPrint(e);
        }
        return false;
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (!CheckDataUtils.isEmpty(subscriptions)) {
            for (Subscription subscription : subscriptions) {
                subscription.unsubscribe();
            }
        }
        try {
            super.onDestroy();
        } catch (Exception e) {
            LSUtil.EPrint(e);
        }
        if (Config.LOGABLE) {
            LSUtil.Ld("LifecycleLeeOne", getClass().getSimpleName() + "-->ActivityLifeCycle:" + activityManager
                    .getRunningTasks(Integer.MAX_VALUE).size() + "-->onDestroy");
        }
        try {
            AppManager.I().removeActivity(this);
        } catch (Exception e) {
            LSUtil.EPrint(e);
        }

    }

    /**
     * 跳转到选择图片页面
     *
     * @param showCamera  当type参数失效时  展示相册 是否展示照相的按钮
     * @param selectCount 可以选择的总数
     * @param selectMode  {@link me.nereo.multi_image_selector.MultiImageSelectorNoUploadActivity#MODE_MULTI MODE_MULTI }
     *                    可多选，{@link me.nereo.multi_image_selector.MultiImageSelectorNoUploadActivity#MODE_SINGLE MODE_SINGLE}
     *                    单选
     *                    直接跳转到相册，{@link me.nereo.multi_image_selector.MultiImageSelectorNoUploadActivity#CAMERA CAMERA}
     *                    直接跳转到相机
     */
    protected void showPictures(boolean showCamera, int selectCount, int selectMode,
                                ArrayList<String> paths) {
        Intent intent = new Intent(this, MultiImageSelectorNoUploadActivity.class);
// 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorNoUploadActivity.EXTRA_SHOW_CAMERA, showCamera);
// 最大图片选择数量
        intent.putExtra(MultiImageSelectorNoUploadActivity.EXTRA_SELECT_COUNT, selectCount);
// 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorNoUploadActivity.EXTRA_SELECT_MODE, selectMode);
// 默认选择图片,回填选项(支持String ArrayList)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, paths);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    protected void showPictures(ArrayList<String> paths) {
        Intent intent = new Intent(this, MultiImageSelectorNoUploadActivity.class);
// 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorNoUploadActivity.EXTRA_SHOW_CAMERA, true);
// 最大图片选择数量
        intent.putExtra(MultiImageSelectorNoUploadActivity.EXTRA_SELECT_COUNT, 9);
// 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorNoUploadActivity.EXTRA_SELECT_MODE, MultiImageSelectorNoUploadActivity
                .MODE_MULTI);
// 默认选择图片,回填选项(支持String ArrayList)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, paths);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    protected ArrayList<String> getImageResult(int requestCode, int resultCode, Intent data) {
        return data.getStringArrayListExtra(MultiImageSelectorNoUploadActivity
                .EXTRA_RESULT);
    }

    protected boolean isRequstImage(int requestCode, int resultCode, Intent data) {
        return resultCode == RESULT_OK && requestCode == REQUEST_IMAGE && data != null;
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;

        try {
            unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
            for (EMConversation conversation : EMClient.getInstance().chatManager().getAllConversations().values()) {
                if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                    chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (this == null) {
            AppManager.I().finishAllActivity();
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        super.onSaveInstanceState(outState);
    }

}
