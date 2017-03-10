package com.zhanghao.speed.speed.mvp.view.me;

import android.support.v7.widget.LinearLayoutManager;

import com.zhanghao.speed.speed.R;
import com.zhanghao.speed.speed.base.BaseListFragment;
import com.zhanghao.speed.speed.base.baservadapter.CommonAdapter;
import com.zhanghao.speed.speed.base.baservadapter.ViewHolder;
import com.zhanghao.speed.speed.mvp.presenter.MeFragmentPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by PC on 2017/3/6.
 * 作者 ：张浩
 * 作用：
 */

public class MeFragment extends BaseListFragment implements MeFragmentView {

    private List<String> data = new ArrayList<>();
    @Inject
    public MeFragmentPresenterImpl mPresenter;


    @Override
    public void initViews() {
        super.initViews();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRefreshLayout.setOverScrollBottomShow(false);

        for (int i = 0; i < 30; i++) {
            data.add("zhanghao" + i);
        }

        mRecyclerView.setAdapter(new CommonAdapter<String>(mActivity, R.layout.item_rv, data) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_item, s);
            }
        });

    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }


    @Override
    public void initData() {
        mPresenter.injectSuccess();

    }

    @Override
    public void initToolbar() {

    }


    @Override
    public void initDagger() {
        mFragmentComponent.inject(this);
    }


//    @OnClick({R.id.tvCicleMenu, R.id.tvNineGridView})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tvCicleMenu:
//                startActivity(new Intent(mActivity, TestPaintActivity.class));
//                break;
//            case R.id.tvNineGridView:
//                break;
//        }
//    }
}
