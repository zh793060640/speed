package com.zhanghao.speed.mvp;

import com.zhanghao.core.api.ImageBean;
import com.zhanghao.core.api.RetrofitClient;
import com.zhanghao.core.api.RetrofitParameterBuilder;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * 作者： zhanghao on 2017/7/19.
 * 功能：${des}
 */

public class MainModel implements MainContract.Model {


    @Override
    public Observable<ImageBean> uploadPhoto(String path) {
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)//表单类型
//                .addFormDataPart("userId", "89416");
//        List<MultipartBody.Part> body = RetrofitClient.prepareFilePart("upload", new File(path), builder);
        Map<String, RequestBody> params= RetrofitParameterBuilder.newBuilder()
                .addParameter("userId", "89416")
              . addParameter("Photo", new File(path)).build();
        return RetrofitClient.getApiService().uploadPhoto(params);
    }
}
