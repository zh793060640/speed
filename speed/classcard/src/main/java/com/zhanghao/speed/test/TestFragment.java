package com.zhanghao.speed.test;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhanghao.core.base.BaseFragment;
import com.zhanghao.core.base.BaseModle;
import com.zhanghao.core.base.BasePresenter;
import com.zhanghao.speed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： zhanghao on 2017/10/10.
 * 功能：${des}
 */

public class TestFragment extends BaseFragment<BasePresenter, BaseModle> {
    SmartRefreshLayout smartRefreshLayout;
    RecyclerView recyclerView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_test;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        recyclerView = findView(R.id.recyclerView);
        smartRefreshLayout = findView(R.id.smartRefreshLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayout.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add("fragment_test" + i);
        }

        TestAdapter adapter = new TestAdapter(activity, R.layout.item_test, data);
        recyclerView.setAdapter(adapter);
    }
}
