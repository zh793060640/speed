package com.zhanghao.speed.mvp;

import com.zhanghao.core.bean.RegisterInfo;
import com.zhanghao.core.base.BaseModle;
import com.zhanghao.core.base.BasePresenter;
import com.zhanghao.core.base.BaseRespose;
import com.zhanghao.core.base.BaseView;

import io.reactivex.Observable;

/**
 * 作者： zhanghao on 2017/7/19.
 * 功能：${des}
 */

public interface MainContract {

    interface Model extends BaseModle {
        public Observable<BaseRespose<RegisterInfo>> checkRegister(String mac);
    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void checkRegister(String mac);
    }
}
