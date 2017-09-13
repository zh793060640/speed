package com.leeone.core.api;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者： zhanghao on 2017/7/12.
 * 功能：${des}
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    public static int READ_TIMEOUT = 60;
    public static int WRIT_TIMEOUT = 60;
    public static int CONNECT_TIMEOUT = 60;
    public static String baseUrl = "http://test.leeonedu.com/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRIT_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);

    public static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (RetrofitClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //适配rxjava2.0
                            .client(getOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static void init(String Url){
        baseUrl = Url;
    }
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient client = httpClient
                // 日志拦截器
                //.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
        return client;
    }

    public static  HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog","retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
