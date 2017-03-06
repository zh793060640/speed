package com.zhanghao.speed.speed.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhanghao.speed.speed.FastApplication;
import com.zhanghao.speed.speed.dagger.component.ActivityComponent;
import com.zhanghao.speed.speed.dagger.component.DaggerActivityComponent;
import com.zhanghao.speed.speed.dagger.module.ActivityModule;

/**
 * Created by PC on 2017/3/6.
 * 作者 ：张浩
 * 作用：
 */

public abstract  class DaggerActivity extends BaseActivity {
    public ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).applicationComponent(FastApplication.getAppComponent()).build();
        mActivityComponent.inject(this);
        super.onCreate(savedInstanceState);
    }
}
