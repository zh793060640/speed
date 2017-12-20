package com.zhanghao.speed.test;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanghao.core.base.MyTitleBar;
import com.zhanghao.speed.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： zhanghao on 2017/10/10.
 * 功能：${des}
 */

public class CoordinatorLayoutActivity extends AppCompatActivity {
    TabLayout tablayout;
    ViewPager viewPager;
    MyTitleBar myTitleBar;
    FragmentAdapter pageAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout);
        tablayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myTitleBar = (MyTitleBar) findViewById(R.id.myTitleBar);
        mFragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mFragments.add(new TestFragment());
        }
        pageAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(pageAdapter);
        tablayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }
        tablayout.getTabAt(0).select();
        TextView viewById = (TextView) tablayout.getTabAt(0).getCustomView().findViewById(R.id.tvName);
        viewById.setTextColor(getResources().getColor(R.color.green));
        myTitleBar.setTitle("CoordinatorLayoutActivity");
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tablayout, 30, 30);
            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView viewById = (TextView) tab.getCustomView().findViewById(R.id.tvName);
                viewById.setTextColor(getResources().getColor(R.color.green));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView viewById = (TextView) tab.getCustomView().findViewById(R.id.tvName);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        TextView tv = (TextView) view.findViewById(R.id.tvName);
        tv.setText("测试" + position);
        return view;
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
