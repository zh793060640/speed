package com.zhanghao.speed.mvp;

import com.zhanghao.core.base.BaseObserver;
import com.zhanghao.core.base.baserx.RxHelper;
import com.zhanghao.core.bean.RegisterInfo;
import com.zhanghao.core.utils.LogUtils;

import io.reactivex.disposables.Disposable;

/**
 * 作者： zhanghao on 2017/7/19.
 * 功能：${des}
 */

public class MainPresenter extends MainContract.Presenter {


    @Override
    public void checkRegister(String mac) {

        mModel.checkRegister(mac).compose(RxHelper.<RegisterInfo>handleResult()).subscribe(new BaseObserver<RegisterInfo>() {

            @Override
            public void onSubscribe(Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void on_Next(RegisterInfo registerInfo) {

            }

            @Override
            public void on_Errort(Throwable e) {
                LogUtils.e(e.getMessage());
            }
        });
    }
}
