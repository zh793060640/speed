package com.zhanghao.speed.speed.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhanghao.speed.speed.dagger.component.DaggerFragmentComponent;
import com.zhanghao.speed.speed.dagger.component.FragmentComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by PC on 2017/2/15.
 * 作者 ：张浩
 * 作用：
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    public BaseActivity mActivity;
    public FragmentComponent mFragmentComponent;
    protected BasePresenter mPresenter;
    private Unbinder unbinder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = getCreateView(inflater, container);
        unbinder = ButterKnife.bind(this, layoutView);
        return layoutView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mFragmentComponent = DaggerFragmentComponent
                .builder()
                .activityComponent(((DaggerActivity) getBaseActivity()).mActivityComponent)
                .build();
        initDagger();
        initViews();
        initToolbar();
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    public BaseActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }

    @Override
    public void showProgress(String message) {
        if (getStatus() && getBaseActivity() != null) {
            getBaseActivity().showProgress(message);
        }
    }

    @Override
    public void showProgress(String message, int progress) {
        if (getStatus() && getBaseActivity() != null) {
            getBaseActivity().showProgress(message, progress);
        }
    }

    @Override
    public void hideProgress() {
        if (getStatus() && getBaseActivity() != null) {

            getBaseActivity().hideProgress();
        }

    }


    @Override
    public void showToast(String msg) {
        if (getStatus() && getBaseActivity() != null) {
            getBaseActivity().showToast(msg);

        }
    }

    @Override
    public void close() {

    }

    /**
     * 获取当前Fragment状态
     *
     * @return true为正常 false为未加载或正在删除
     */
    private boolean getStatus() {
        return (isAdded() && !isRemoving());
    }

    public void showWarningDialog(String content, String title, SweetAlertDialog.OnSweetClickListener listener) {
        getBaseActivity().showWarningDialog(title, content, listener);
    }

    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();

    /**
     * 初始化Views
     */
    public abstract void initViews();

    public abstract void initData();

    public abstract void initToolbar();

    public abstract void initDagger();
}
