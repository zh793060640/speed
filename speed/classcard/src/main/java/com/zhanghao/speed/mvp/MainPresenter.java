package com.zhanghao.speed.mvp;

import android.support.annotation.NonNull;

import com.zhanghao.core.api.ImageBean;
import com.zhanghao.core.api.RetrofitClient;
import com.zhanghao.core.base.BaseObserver;
import com.zhanghao.core.utils.LogUtils;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 作者： zhanghao on 2017/7/19.
 * 功能：${des}
 */

public class MainPresenter extends MainContract.Presenter {


    @Override
    public void uploadPhoto(String path) {

        mModel.uploadPhoto(path).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<ImageBean>() {

            @Override
            public void onSubscribe(Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void on_Next(ImageBean registerInfo) {
                LogUtils.e(registerInfo.getCode());
            }

            @Override
            public void on_Errort(Throwable e) {

            }

        });
    }

    public void getInfo() {
        RetrofitClient.getApiService().getVipInfo("70030").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<ResponseBody>() {


            @Override
            public void on_Next(ResponseBody value) {
                try {
                    LogUtils.d(value.bytes().toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void on_Errort(Throwable e) {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }
        });
    }
}
