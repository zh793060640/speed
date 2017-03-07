package com.zhanghao.speed.speed.mvp.presenter;

import android.support.annotation.NonNull;

import com.zhanghao.speed.speed.base.BaseView;
import com.zhanghao.speed.speed.mvp.view.me.MeFragmentView;
import com.zhanghao.speed.speed.utils.LogUtils;

import javax.inject.Inject;

/**
 * Created by PC on 2017/3/6.
 * 作者 ：张浩
 * 作用：
 */

public class MeFragmentPresenterImpl implements MeFragmentPresenter {
    private MeFragmentView mFragmentView;

    @Inject
    public MeFragmentPresenterImpl() {

    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mFragmentView = (MeFragmentView) view;
    }

    @Override
    public void detachView() {

    }

    public void injectSuccess() {
        LogUtils.Ld("依赖注入成功");
    }
}
