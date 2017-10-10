package com.zhanghao.core.api;

import com.zhanghao.core.ChildInfo;
import com.zhanghao.core.base.BaseRespose;
import com.zhanghao.core.bean.RegisterInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者： zhanghao on 2017/7/12.
 * 功能：${des}
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("/Api/User/returnChildInfo")
    Observable<BaseRespose<ChildInfo>> getChildInfo (@Field("studentId") String studentId);


    @FormUrlEncoded
    @POST("/Api/ClassBrand/getClassIdByBrandDevice")
    Observable<BaseRespose<RegisterInfo>> getRegisterStatus (@Field("brandDeviceId") String brandDeviceId);
}