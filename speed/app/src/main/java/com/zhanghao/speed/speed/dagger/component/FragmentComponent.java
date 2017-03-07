package com.zhanghao.speed.speed.dagger.component;


import com.zhanghao.speed.speed.dagger.scope.FragmentScope;
import com.zhanghao.speed.speed.mvp.view.me.MeFragment;

import dagger.Component;

/**
 * Created by Roger on 2016/4/19.
 * Fragment容器，依赖于ActivityComponent
 * 负责为注入的Fragment提供对象，限定对应的对象的生命周期
 */
@FragmentScope
@Component(dependencies = ActivityComponent.class)
public interface FragmentComponent {

    void inject(MeFragment fragment);

}

