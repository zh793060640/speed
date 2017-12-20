package com.zhanghao.speed.mvp;

import com.zhanghao.core.api.ImageBean;
import com.zhanghao.core.base.BaseModle;
import com.zhanghao.core.base.BasePresenter;
import com.zhanghao.core.base.BaseView;

import io.reactivex.Observable;

/**
 * 作者： zhanghao on 2017/7/19.
 * 功能：${des}
 */

public interface MainContract {

    interface Model extends BaseModle {
        public Observable<ImageBean> uploadPhoto(String path);
    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void uploadPhoto(String path);
    }
}
