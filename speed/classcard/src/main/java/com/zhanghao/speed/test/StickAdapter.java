package com.zhanghao.speed.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhanghao.speed.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * 作者： zhanghao on 2017/11/22.
 * 功能：${des}
 */

public class StickAdapter extends CommonAdapter<String> implements StickyListHeadersAdapter {
    public StickAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.test, item);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_stick_header, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.header);
        tv.setText("stici" + position);
        return convertView;
    }


    // getHeaderId决定header出现的时机，如果当前的headerid和前一个headerid不同时，就会显示。
    @Override
    public long getHeaderId(int position) {
        return mDatas.get(position).subSequence(0, 3).charAt(0);  //选中变化的那个量就行
    }

}
