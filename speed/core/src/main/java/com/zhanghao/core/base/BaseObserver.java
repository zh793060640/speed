package com.zhanghao.core.base;

import com.zhanghao.core.utils.LogUtils;

import io.reactivex.Observer;

/**
 * 作者： zhanghao on 2017/7/19.
 * 功能：${des}
 */

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onNext(T value) {
        on_Next(value);
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e(e.getMessage());
        on_Errort(e);
    }

    @Override
    public void onComplete() {

    }

    public abstract void on_Next(T value);

    public abstract void on_Errort(Throwable e);

}
