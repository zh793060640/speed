package com.zhanghao.speed.speed.dagger.module;

import android.content.Context;

import com.zhanghao.speed.speed.AppConfig;
import com.zhanghao.speed.speed.FastApplication;
import com.zhanghao.speed.speed.bean.DaoSession;
import com.zhanghao.speed.speed.dagger.scope.ContextLife;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roger on 2016/4/13.
 * 在Application里边生成的全局对象
 */
@Module
public class ApplicationModule {

    public static final String END_POINT = "http://www.baidu.com";
    private final FastApplication mApplication;

    public ApplicationModule(FastApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @ContextLife()
    public Context provideContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public FastApplication getApplication() {
        return mApplication;
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit.Builder retrofit = new Retrofit.Builder().client(client).baseUrl(END_POINT);
        if (AppConfig.LOGABLE) {
            retrofit.addConverterFactory(GsonConverterFactory.create());
        }
        return retrofit.build();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .build();


        return client;
    }

    @Provides
    @Singleton
    public DaoSession getDaoSession(FastApplication application) {
        return application.getDaoSession();
    }

}
