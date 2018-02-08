package com.zhanghao.speed.chart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.zhanghao.speed.R;

/**
 * 作者： zhanghao on 2018/2/8.
 * 功能：${des}
 */

public class XYMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */

    private TextView tvContent;
    private IAxisValueFormatter xAxisValueFormatter;
    public XYMarkerView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.custom_marker_view);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = (TextView) findViewById(R.id.tv_content);

    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("12月5日"+"\n"+e.getY()+"%");
        super.refreshContent(e, highlight);
    }
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
