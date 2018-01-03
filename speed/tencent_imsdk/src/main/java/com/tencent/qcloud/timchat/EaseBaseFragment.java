package com.tencent.qcloud.timchat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.leeone.leeonecore.view.EmptyLayout;
import com.tencent.qcloud.timchat.ui.customview.EaseTitleBar;

public abstract class EaseBaseFragment extends Fragment {
    protected EaseTitleBar titleBar;
    protected InputMethodManager inputMethodManager;
    protected static String TAG;
    public EmptyLayout mEmptyLayout;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        titleBar = (EaseTitleBar) getView().findViewById(R.id.title_bar);
        mEmptyLayout= (EmptyLayout) getView().findViewById(R.id.emptyLayout);
        initView();
        setUpView();
    }

    /**
     * 显示标题栏
     */
    public void showTitleBar() {
        if (titleBar != null) {
            titleBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏标题栏
     */
    public void hideTitleBar() {
        if (titleBar != null) {
            titleBar.setVisibility(View.GONE);
        }
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置属性，监听等
     */
    protected abstract void setUpView();

    @Override
    public void onResume() {
        if (getActivity() != null && getActivity() instanceof EaseBaseActivity) {
            ((EaseBaseActivity) getActivity()).registerMyTouchListener(mTouchListener);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        if (getActivity() != null && getActivity() instanceof EaseBaseActivity) {
            ((EaseBaseActivity) getActivity()).unRegisterMyTouchListener(mTouchListener);
        }
        super.onStop();
    }

    /**
     * Fragment中，注册 接收MainActivity的Touch回调的对象
     * 重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理
     */
    protected EaseBaseActivity.MyTouchListener mTouchListener = new EaseBaseActivity.MyTouchListener() {
        @Override
        public void onTouchEvent(MotionEvent event) {
            if (getActivity() != null) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    View v = getActivity().getCurrentFocus();
                    if (isShouldHideInput(v, event)) {
                        //隐藏虚拟键盘
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context
                                .INPUT_METHOD_SERVICE);
                        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams
                                .SOFT_INPUT_STATE_HIDDEN) {
                            if (getActivity().getCurrentFocus() != null)
                                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                }
            }
        }
    };

    private boolean isShouldHideInput(View v, MotionEvent event) {
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

}
