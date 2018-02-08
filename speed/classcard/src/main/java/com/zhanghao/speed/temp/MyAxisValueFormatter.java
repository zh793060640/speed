package com.zhanghao.speed.temp;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * 作者： zhanghao on 2018/2/8.
 * 功能：${des}
 */

public class MyAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return value+"%";
    }
}
