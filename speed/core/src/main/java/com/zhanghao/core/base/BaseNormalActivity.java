package com.zhanghao.core.base;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zhanghao.core.R;
import com.zhanghao.core.shortcutbadger.ShortcutBadger;
import com.zhanghao.core.ui.EmptyLayout;
import com.zhanghao.core.utils.AppManager;
import com.zhanghao.core.utils.Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.EasyPermissions;


public abstract class BaseNormalActivity  extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    protected FragmentManager fm;
    protected BaseNormalActivity activity = this;
    protected Intent extraIntent;
    protected static String TAG;
    private ActivityManager activityManager;
    public MyTitleBar myTitleBar;
    private FrameLayout flContentl;
    private EmptyLayout emptyLayout;
    private SweetAlertDialog mDialog;
    private Unbinder mUnbinder;
    boolean showBaseTitle = false;   //是否默认的头布局和空数据view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        Log.i(TAG, "onCreate: ");

        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        extraIntent = getIntent();
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));

        mDialog.setCancelable(false);
        //理论上应该放在launcher activity,放在基类中所有集成此库的app都可以避免此问题
        if (!isTaskRoot()) {
            String action = extraIntent.getAction();
            if (extraIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        // setContentView(R.layout.activity_base);
        showBaseTitle = isShowBaseTitle();
        if (showBaseTitle) {
            ViewGroup rootView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
            myTitleBar = (MyTitleBar) rootView.findViewById(R.id.titlebar);
            flContentl = (FrameLayout) rootView.findViewById(R.id.flContent);
            emptyLayout = (EmptyLayout) rootView.findViewById(R.id.emptyLayout);

            if (getContentView() != 0) {
                View content = LayoutInflater.from(this).inflate(getContentView(), null);
                flContentl.removeAllViews();
                flContentl.addView(content);
            }
            setContentView(rootView);
        } else {
            setContentView(getContentView());
        }

        mUnbinder = ButterKnife.bind(this);

        fm = getSupportFragmentManager();
        this.initView();
        this.initData();
        AppManager.I().addActivity(this);
    }

    public boolean isShowBaseTitle() {
        return true;
    }


    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getContentView();


    public void setTitleBarVisible(boolean tmp) {
        if (tmp) {
            myTitleBar.setVisibility(View.VISIBLE);
        } else {
            myTitleBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {


        AppManager.I().removeActivity(this);
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }


    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //设置app图标的红点提醒数量
        if (!Utils.isAppRunningForeground(activity)) {
            int num = 0;
            if (num > 0) {
                ShortcutBadger.applyCount(activity, num);
            } else {
                ShortcutBadger.removeCount(activity);
            }
        }
    }

    public void showLoadingDialog() {
        mDialog.setTitleText("Loading");
        mDialog.show();
    }

    public void showDialog(String title) {
        mDialog.setTitleText(title);
        mDialog.show();
    }

    public void dissmissDialog() {
        mDialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
    }

}
