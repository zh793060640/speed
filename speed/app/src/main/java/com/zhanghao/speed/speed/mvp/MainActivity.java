package com.zhanghao.speed.speed.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhanghao.speed.speed.R;
import com.zhanghao.speed.speed.base.DaggerActivity;
import com.zhanghao.speed.speed.mvp.view.me.MeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends DaggerActivity {
    private List<Fragment> allFragment;
    private MeFragment meFragment;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager manager;


    @Override
    protected void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initViewsAndListener() {
        manager = getSupportFragmentManager();
        mFragmentTransaction = manager.beginTransaction();
        allFragment = new ArrayList<>();
        meFragment = new MeFragment();
        allFragment.add(meFragment);
        mFragmentTransaction.add(R.id.flContainer, meFragment, "meFragment").commitAllowingStateLoss();

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void close() {

    }

}
