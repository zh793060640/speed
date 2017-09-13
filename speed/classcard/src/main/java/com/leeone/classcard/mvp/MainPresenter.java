package com.leeone.classcard.mvp;

import com.leeone.core.base.BaseObserver;
import com.leeone.core.base.baserx.RxHelper;
import com.leeone.core.bean.RegisterInfo;
import com.leeone.core.utils.LogUtils;

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
                if (registerInfo.getClassId() == 0) {
                    mView.checkRegister(false);
                } else {
                    mView.checkRegister(true);
                }
            }

            @Override
            public void on_Errort(Throwable e) {
                LogUtils.e(e.getMessage());
            }
        });
    }
}
