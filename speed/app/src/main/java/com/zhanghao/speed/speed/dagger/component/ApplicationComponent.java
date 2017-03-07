package com.zhanghao.speed.speed.dagger.component;

import android.app.Application;
import android.content.Context;

import com.zhanghao.speed.speed.FastApplication;
import com.zhanghao.speed.speed.dagger.module.ApplicationModule;
import com.zhanghao.speed.speed.dagger.scope.ContextLife;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roger on 2016/4/13.
 * <p>
 * Application容器，负责提供几个全局使用到的对象
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    @ContextLife()
    Context getContext();

    Application getApplication();

    void inject(FastApplication fastApplication);
}
