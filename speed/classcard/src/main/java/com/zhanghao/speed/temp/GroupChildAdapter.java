package com.zhanghao.speed.temp;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.zhanghao.speed.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 作者： zhanghao on 2018/2/5.
 * 功能：${des}
 */

public class GroupChildAdapter extends CommonAdapter<SectionInfo> {
    public GroupChildAdapter(Context context, int layoutId, List<SectionInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SectionInfo item, int position) {
        TextView tv_name = viewHolder.getView(R.id.tv_name);
        if (item.isSelected) {
            tv_name.setBackgroundColor(Color.RED);
        }else {
            tv_name.setBackgroundColor(Color.WHITE);
        }
        viewHolder.setText(R.id.tv_name, item.name);
    }

}
