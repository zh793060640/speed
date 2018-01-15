package com.zhanghao.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.zhanghao.core.R;
import com.zhanghao.core.base.baserx.TUtil;
import com.zhanghao.core.ui.EmptyLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/*************************************************************************************************
 * 作   者： 高永好
 * 完成日期：2017-04-20 09:52
 * 说明：
 ************************************************************************************************/

public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment implements View.OnClickListener {
    //TODO:如果Fragment不需要MVP，子类需要写成extends BaseFragment<WorkPresenter, WorkModel>，否则报错
    protected View rootView;
    public T mPresenter;
    public E mModel;
    public AppCompatActivity activity;
    private InputMethodManager inputMethodManager;
    private String TAG;
    public MyTitleBar myTitleBar;
    private FrameLayout flContentl;
    private EmptyLayout emptyLayout;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        activity = (AppCompatActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_base, null);
        myTitleBar = (MyTitleBar) rootView.findViewById(R.id.titlebar);
        flContentl = (FrameLayout) rootView.findViewById(R.id.flContent);
        emptyLayout = (EmptyLayout) rootView.findViewById(R.id.emptyLayout);


        if (getContentView() != 0) {
            View contentView = LayoutInflater.from(activity).inflate(getContentView(), container,false);
            flContentl.removeAllViews();
            flContentl.addView(contentView);
        }
        mUnbinder = ButterKnife.bind(this, rootView);
//        if (rootView == null)
//            rootView = inflater.inflate(getContentView(), container, false);

        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        initPresenter();
        initView();
        initData();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract int getContentView();

    public abstract void initPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected <T extends View> T findView(int id) {
        return (T) rootView.findViewById(id);
    }
}
