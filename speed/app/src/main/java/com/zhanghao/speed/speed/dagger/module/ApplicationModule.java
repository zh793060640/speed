package com.zhanghao.speed.speed.dagger.module;

import android.app.Application;
import android.content.Context;

import com.zhanghao.speed.speed.dagger.scope.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roger on 2016/4/13.
 * 在Application里边生成的全局对象
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @ContextLife()
    public Context provideContext() {
        return mApplication.getApplicationContext();
    }

}
