package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.tencent.imsdk.TIMLocationElem;
import com.tencent.imsdk.TIMMessage;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;
import com.tencent.qcloud.timchat.ui.BaiduMapActivity;

/**
 * 作者： zhanghao on 2018/3/2.
 * 功能：${des}
 */

public class LocationMessage extends Message {

    public LocationMessage(TIMMessage message) {
        this.message = message;
    }

    public LocationMessage(String des, Double longitude, Double latitude) {
        message = new TIMMessage();
        TIMLocationElem elem = new TIMLocationElem();
        elem.setDesc(des);
        elem.setLatitude(latitude);
        elem.setLongitude(longitude);
        message.addElement(elem);
    }

    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, final Context context) {
        clearView(viewHolder);
        if (checkRevoke(viewHolder)) return;
        final TIMLocationElem e = (TIMLocationElem) message.getElement(0);

        TextView tv = new TextView(MyApplication.getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tv.setTextColor(MyApplication.getContext().getResources().getColor(isSelf() ? R.color.white : R.color.black));
        tv.setText(e.getDesc());
        getBubbleView(viewHolder).addView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaiduMapActivity.toBaiduMapActivity(context, e.getDesc(), e.getLatitude(), e.getLongitude());
            }
        });
        showStatus(viewHolder);
    }

    @Override
    public String getSummary() {
        return "[位置]";
    }

    @Override
    public void save() {

    }
}
