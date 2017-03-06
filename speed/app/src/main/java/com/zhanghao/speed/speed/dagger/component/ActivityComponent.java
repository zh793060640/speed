package com.zhanghao.speed.speed.dagger.component;

import android.app.Activity;
import android.content.Context;

import com.zhanghao.speed.speed.base.DaggerActivity;
import com.zhanghao.speed.speed.dagger.module.ActivityModule;
import com.zhanghao.speed.speed.dagger.scope.ActivityScope;
import com.zhanghao.speed.speed.dagger.scope.ContextLife;

import dagger.Component;

/**
 * Created by Roger on 2016/4/13.
 * Activity的容器，依赖ApplicationComponent
 * 负责Activity的注入对象的生命周期
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife()
    Context getApplicationContext();


    Activity getActivity();

    void inject(DaggerActivity activity);

}
