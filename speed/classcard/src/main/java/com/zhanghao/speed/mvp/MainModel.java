package com.zhanghao.speed.mvp;

import com.zhanghao.core.api.RetrofitClient;
import com.zhanghao.core.base.BaseRespose;
import com.zhanghao.core.bean.RegisterInfo;

import io.reactivex.Observable;

/**
 * 作者： zhanghao on 2017/7/19.
 * 功能：${des}
 */

public class MainModel implements MainContract.Model {

    @Override
    public Observable<BaseRespose<RegisterInfo>> checkRegister(String mac) {
        return RetrofitClient.getApiService().getRegisterStatus(mac);
    }
}
