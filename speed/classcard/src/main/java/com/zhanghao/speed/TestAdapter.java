package com.zhanghao.speed;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 作者： zhanghao on 2017/9/21.
 * 功能：${des}
 */

public class TestAdapter extends CommonAdapter <String>{
    public TestAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }


    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.test, s);
    }

}
