package com.zhanghao.speed.speed;

import android.app.Application;

import com.zhanghao.speed.speed.dagger.component.ApplicationComponent;
import com.zhanghao.speed.speed.dagger.component.DaggerApplicationComponent;
import com.zhanghao.speed.speed.dagger.module.ApplicationModule;

/**
 * Created by PC on 2017/2/15.
 * 作者 ：张浩
 * 作用：
 */

public class FastApplication extends Application{
    private static ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppComponent == null)
            mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mAppComponent.inject(this);
    }

    public static ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}
