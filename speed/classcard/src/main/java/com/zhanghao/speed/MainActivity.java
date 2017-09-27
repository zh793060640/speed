package com.zhanghao.speed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhanghao.core.base.BaseActivity;
import com.zhanghao.core.utils.GalleryFinalUtils;
import com.zhanghao.core.zbar.ZbarActivity;
import com.zhanghao.speed.mvp.MainContract;
import com.zhanghao.speed.mvp.MainModel;
import com.zhanghao.speed.mvp.MainPresenter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;


public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View, GalleryFinal.OnHanlderResultCallback {

    RecyclerView recyclerView;
    SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        myTitleBar.setTitle("测试");
        recyclerView = findView(R.id.recyclerView);
        smartRefreshLayout = findView(R.id.smartRefreshLayout);
        List<String> data = new ArrayList<>();
        data.add("图片选择");
        data.add("二维码");
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.finishLoadmore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.finishRefresh();
                    }
                }, 1000);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter adapter = new TestAdapter(activity, R.layout.item_test, data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position) {
                    case 0:
                        GalleryFinalUtils.openGalleryMuti(9, MainActivity.this);
                        break;
                    case 1:
                        Intent intent = new Intent(activity, ZbarActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }


    @Override
    protected int getContentView() {

        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {

    }
}
