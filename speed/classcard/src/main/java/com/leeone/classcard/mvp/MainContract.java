package com.leeone.classcard.mvp;

import com.leeone.core.bean.RegisterInfo;
import com.leeone.core.base.BaseModle;
import com.leeone.core.base.BasePresenter;
import com.leeone.core.base.BaseRespose;
import com.leeone.core.base.BaseView;

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
        public void checkRegister(boolean status);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void checkRegister(String mac);
    }
}
