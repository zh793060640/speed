package com.zhanghao.speed.speed.base;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout;
import com.zhanghao.speed.speed.R;

import butterknife.BindView;

/**
 * Created by PC on 2017/3/7.
 * 作者 ：张浩
 * 作用：
 */

public abstract class BaseListFragment extends BaseFragment {
    @BindView(R.id.mRecyclerView)
    public RecyclerView mRecyclerView;
    @BindView(R.id.mRefreshLayout)
    public TwinklingRefreshLayout mRefreshLayout;

    @Override
    public void initViews() {
        BezierLayout headerView = new BezierLayout(mActivity);
        //  ProgressLayout headerView = new ProgressLayout(mActivity);
        mRefreshLayout.setHeaderView(headerView);
        // mRefreshLayout.setBottomView(new LoadingView(mActivity));
        mRefreshLayout.setWaveHeight(140);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMore();
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);

            }

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_list;
    }

    public abstract void refresh();

    public abstract void loadMore();


    @Override
    public void initData() {

    }

    @Override
    public void initToolbar() {

    }

    @Override
    public void initDagger() {
    }

}
