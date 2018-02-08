package com.zhanghao.speed.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * 作者： zhanghao on 2018/2/8.
 * 功能：${des}
 */

public class DayAxisValueFormatter implements IAxisValueFormatter {
    protected String[] mMonths = new String[]{
            "周一", "周二", "周三", "周四", "周五", "周六", "周天"
    };

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int days = (int) value;
        return mMonths[days];
    }
}
