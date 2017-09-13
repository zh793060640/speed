package com.leeone.core.base;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.leeone.core.R;
import com.leeone.core.base.baserx.TUtil;


/*************************************************************************************************
 * 作   者： 高永好
 * 完成日期：2017-04-20 09:52
 * 说明：
 ************************************************************************************************/

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModle> extends AppCompatActivity {

    protected FragmentManager fm;
    public T mPresenter;
    public E mModel;
    protected AppCompatActivity activity = this;
    protected Intent extraIntent;
    protected static String TAG;
    private ActivityManager activityManager;
    public MyTitleBar myTitleBar;
    private FrameLayout flContentl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_base);

        myTitleBar = (MyTitleBar) findViewById(R.id.titlebar);
        flContentl = (FrameLayout) findViewById(R.id.flContent);
        if (getContentView() != 0) {
            View content = LayoutInflater.from(this).inflate(getContentView(), null);
            flContentl.removeAllViews();
            flContentl.addView(content);
        }
        fm = getSupportFragmentManager();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        this.initPresenter();
        this.initView();
        this.initData();
    }

    protected abstract void initPresenter();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getContentView();


    public void setTitleBarVisible(boolean tmp){
        if(tmp){
            myTitleBar.setVisibility(View.VISIBLE);
        }else {
            myTitleBar.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onDestroy() {


        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }


    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
