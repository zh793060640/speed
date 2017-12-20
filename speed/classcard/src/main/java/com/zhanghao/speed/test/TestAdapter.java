package com.zhanghao.speed.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhanghao.speed.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 作者： zhanghao on 2017/9/21.
 * 功能：${des}
 */

public class TestAdapter extends MultiItemTypeAdapter<String> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;


    public TestAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 10 == 0 ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_stick_header, parent, false);

            return new ViewHolder(mContext, view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        return new ViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if(type==ITEM_VIEW_TYPE_HEADER){
            holder.setText(R.id.header,"标题");
        }else {
            holder.setText(R.id.test,mDatas.get(position));
        }
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null) {
                    listener.onclick(position);
                }
            }
        });
    }

    public OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public  interface OnItemClickListener {
       void  onclick(int postion);
    }
}
