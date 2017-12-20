package com.zhanghao.core.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhanghao.core.R;

/**
 * 作者： zhanghao on 2017/10/20.
 * 功能：${des}
 */

public class BaseRefreshView extends FrameLayout {
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private int page = 1;
    private int pageSize = 10;
    private RefreshListener mRefreshLi;
    private RecyclerView.Adapter mAdapter;
    public BaseRefreshView(@NonNull Context context) {
        this(context, null);
    }

    public BaseRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRefreshView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.base_refreshview, null);
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mRefreshLi != null) {
                    page = mAdapter.getItemCount() / pageSize + 1;
                    mRefreshLi.loadMore(page);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                if (mRefreshLi != null) {
                    mRefreshLi.refresh();
                }
            }
        });
        addView(view);
    }


    public interface RefreshListener {
        public void refresh();

        public void loadMore(int page);
    }

    public void setRefreshListener(RefreshListener mRefreshLi) {
        this.mRefreshLi = mRefreshLi;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        recyclerView.setAdapter(mAdapter);
    }

    public void setCanRefresh(boolean canRefresh) {
        smartRefreshLayout.setEnableRefresh(canRefresh);
    }

    public void setRefreshAndLoadFinish() {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmore();
    }

    public void autoRefresh(int delay) {
        smartRefreshLayout.autoRefresh(delay);
    }
}
