package com.zhanghao.speed.speed.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.zhanghao.speed.speed.R;
import com.zhanghao.speed.speed.utils.AppManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by PC on 2017/2/15.
 * 作者 ：张浩
 * 作用：
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public SweetAlertDialog mProgressDialog;
    public AppCompatActivity mActivity;
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue));
        AppManager.getAppManager().addActivity(this);
        setContentView(getContentView());
        unbinder =  ButterKnife.bind(this);
        initPresenter();
        initToolbar();
        initViewsAndListener();
        initData();
    }

    @Override
    public void showProgress(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.setTitleText(message);
        mProgressDialog.show();

    }

    @Override
    public void showProgress(String message, int progress) {
        mProgressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        mProgressDialog.setTitleText(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.getProgressHelper().setProgress(progress);
        mProgressDialog.show();

    }

    @Override
    public void showToast(String msg) {
        if (!isFinishing())
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

    }


    public void showWarningDialog(String title, String content, SweetAlertDialog.OnSweetClickListener listener) {
        SweetAlertDialog mWarningDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setConfirmText("确定")
                .setCancelText("取消")
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(listener);
        mWarningDialog.show();
    }

    public void showErrorDialog(String title, String content, SweetAlertDialog.OnSweetClickListener listener) {
        SweetAlertDialog mErrorDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setConfirmText("确定")
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(listener);
        mErrorDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // 设置tag，不然下面 findFragmentByTag(tag)找不到
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            getSupportFragmentManager().popBackStack(tag, 0);
        }
    }

    protected abstract void initData();


    public abstract int getContentView();

    public abstract void initViewsAndListener();

    public abstract void initPresenter();

    public abstract void initToolbar();
}
