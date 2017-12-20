package com.zhanghao.core.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * 作者： zhanghao on 2017/7/12.
 * 功能：${des}
 */

//@FormUrlEncoded  post请求  参数用@Field
//@Multipart 上传图片
public interface ApiService {


    //上传单张图片
    @Multipart
    @POST(ApiConstants.uploadImage)
    Observable<ImageBean> uploadPhoto(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST(ApiConstants.getUserInfo)
    @Headers(ApiConstants.CacheControl)
    Observable<ResponseBody> getInfo(@Field(ApiConstants.userId) String userId);

    @FormUrlEncoded
    @POST(ApiConstants.getUserInfo)
    Observable<ResponseBody> getVipInfo(@Field(ApiConstants.userId) String userId);
}
