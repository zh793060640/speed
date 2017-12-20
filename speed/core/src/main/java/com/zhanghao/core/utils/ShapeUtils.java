package com.zhanghao.core.utils;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

/**
 * 作者： zhanghao on 2017/10/12.
 * 功能：${des}
 */

public class ShapeUtils {

    public static void setShape(int color, int radius, View view) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(radius);
        shape.setShape(GradientDrawable.RECTANGLE);
        view.setBackground(shape);
    }

    public static void setSelector(int color1, int color2, int radius, View view) {
        GradientDrawable shape_normal = new GradientDrawable();
        shape_normal.setColor(color1);
        shape_normal.setCornerRadius(radius);
        shape_normal.setShape(GradientDrawable.RECTANGLE);

        GradientDrawable shape_press = new GradientDrawable();
        shape_press.setColor(color2);
        shape_press.setCornerRadius(radius);
        shape_press.setShape(GradientDrawable.RECTANGLE);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed,android.R.attr.state_enabled}, shape_press);//有状态的必须写在上面
        stateListDrawable.addState(new int[]{}, shape_normal);//没有状态的必须写在下面

        view.setBackgroundDrawable(stateListDrawable);//给View设置selector
        view.setClickable(true);
    }
}
