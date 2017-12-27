package com.zhanghao.speed.test;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhanghao.core.base.BaseFragment;
import com.zhanghao.core.base.BaseModel;
import com.zhanghao.core.base.BasePresenter;
import com.zhanghao.speed.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： zhanghao on 2017/10/10.
 * 功能：${des}
 */

public class TestFragment extends BaseFragment<BasePresenter, BaseModel> {
//    SmartRefreshLayout smartRefreshLayout;
//    RecyclerView recyclerView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;

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
//        recyclerView = findView(R.id.recyclerView);
//        smartRefreshLayout = findView(R.id.smartRefreshLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayout.VERTICAL));
        // recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        GridLayoutManager manager = new GridLayoutManager(activity, 2);
        recyclerView.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 10 == 0) {
                    return 2;
                }
                return 1;
            }
        });
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add("fragment_test" + i);
        }

        TestAdapter adapter = new TestAdapter(activity, data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
