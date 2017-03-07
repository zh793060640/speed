package com.zhanghao.speed.speed.mvp.view.me;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhanghao.speed.speed.R;
import com.zhanghao.speed.speed.base.BaseFragment;
import com.zhanghao.speed.speed.mvp.TestPaintActivity;
import com.zhanghao.speed.speed.mvp.presenter.MeFragmentPresenterImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by PC on 2017/3/6.
 * 作者 ：张浩
 * 作用：
 */

public class MeFragment extends BaseFragment implements MeFragmentView {

    @BindView(R.id.tvCicleMenu)
    TextView mTvCicleMenu;
    @BindView(R.id.tvNineGridView)
    TextView mTvNineGridView;
    @Inject
    public MeFragmentPresenterImpl mPresenter;


    @Override
    public int getLayoutRes() {
        return R.layout.mefragment;
    }

    @Override
    public void initViews() {

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


    @OnClick({R.id.tvCicleMenu, R.id.tvNineGridView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCicleMenu:
                startActivity(new Intent(mActivity, TestPaintActivity.class));
                break;
            case R.id.tvNineGridView:
                break;
        }
    }
}
