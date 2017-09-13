package com.leeone.core.base;

import android.content.Context;

import com.leeone.core.base.baserx.RxManager;


/*************************************************************************************************
 * 作   者： 高永好
 * 完成日期：2017-04-20 09:53
 * 说明：
 ************************************************************************************************/

public abstract class BasePresenter<T,E> {
    public T mView;
    public E mModel;
    public Context mContext;
    public RxManager mRxManage = new RxManager();
    public void setVM(T v,E m){
        this.mView=v;
        this.mModel=m;
        this.onStart();
    }
    public void onStart(){

    }
    public void onDestroy(){
        mRxManage.clear();
    }
}
