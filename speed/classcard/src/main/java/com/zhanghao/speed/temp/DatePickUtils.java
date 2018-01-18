package com.zhanghao.speed.temp;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.zhanghao.core.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 作者： zhanghao on 2018/1/18.
 * 功能：${des}
 */

public class DatePickUtils {
    static TimePickerView pvTime = null;

    public static void showPickView(Activity activity, final DatePickListener listener) {

        TimePickerView.Builder builder = new TimePickerView.Builder(activity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                pvTime.dismiss();
                listener.onPickate(TimeUtils.date2String(date, "yyyy-MM-dd"), date);
            }
        });
        pvTime = setStype(builder);
        pvTime.setDate(Calendar.getInstance());
        pvTime.show();
    }

    public static TimePickerView setStype(TimePickerView.Builder builder) {
        return builder
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setLineSpacingMultiplier(1.5f)
                .setTitleText("")//标题文字
                .setDividerColor(Color.GRAY)
                .setBgColor(Color.GRAY)
                .setTextColorCenter(Color.parseColor("#ff9100"))
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.parseColor("#ff9100"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#ff9100"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#ff9100"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false).build();
        //是否显示为对话框样式
    }


    public interface DatePickListener {
        void onPickate(String time, Date date);
    }
}
