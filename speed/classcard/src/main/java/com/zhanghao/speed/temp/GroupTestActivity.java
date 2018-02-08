package com.zhanghao.speed.temp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zhanghao.core.base.BaseNormalActivity;
import com.zhanghao.speed.R;
import com.zhanghao.speed.chart.DayAxisValueFormatter;
import com.zhanghao.speed.chart.XYMarkerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： zhanghao on 2018/2/5.
 * 功能：${des}
 */

public class GroupTestActivity extends BaseNormalActivity implements OnChartValueSelectedListener {
    @BindView(R.id.test_group)
    AnimatedExpandableListView testGroup;
    private HashMap<String, List<SectionInfo>> data = new HashMap<>();
    private List<String> group = new ArrayList<>();
    TestGroupAdapter2 testGroupAdapter;
    private int sign = -1;
    private BarChart mChart;

    @Override
    protected void initView() {
        mChart = (BarChart) findViewById(R.id.spread_pie_chart);

        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);
      //  mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(true);
        //显示边界
        mChart.setDrawBorders(true);
        mChart.setScaleEnabled(false);

        initChart();


        for (int i = 0; i < 20; i++) {
            group.add("group" + i);
            List<SectionInfo> temp = new ArrayList<>();

            for (int i1 = 0; i1 < 8; i1++) {
                SectionInfo info = new SectionInfo();
                info.name = "child" + i1;
                temp.add(info);
            }
            data.put("group" + i, temp);
        }

        testGroupAdapter = new TestGroupAdapter2(activity, group, data);
        testGroup.setAdapter(testGroupAdapter);
        testGroup.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (sign == -1) {
                    // 展开被选的group  
                    testGroup.expandGroupWithAnimation(groupPosition);
                    // 设置被选中的group置于顶端  
                    testGroup.setSelectedGroup(groupPosition);
                    sign = groupPosition;
                } else if (sign == groupPosition) {
                    testGroup.collapseGroupWithAnimation(sign);
                    sign = -1;
                } else {
                    testGroup.collapseGroupWithAnimation(sign);
                    // 展开被选的group  
                    testGroup.expandGroupWithAnimation(groupPosition);
                    // 设置被选中的group置于顶端  
                    testGroup.setSelectedGroup(groupPosition);
                    sign = groupPosition;
                }
                return true;
            }
        });
    }

    private Typeface mTf;

    private void initChart() {

        DayAxisValueFormatter x = new DayAxisValueFormatter();
        MyAxisValueFormatter y = new MyAxisValueFormatter();
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setValueFormatter(x);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);

        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setValueFormatter(y);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setValueFormatter(y);


        // MarkerView markerView = new MarkerView(activity,R.layout.markview_attendance);
        BarData data = generateDataBar(0);
        data.setValueTypeface(mTf);
        XYMarkerView mv = new XYMarkerView(this, x);
        mv.setChartView(mChart);
        mChart.setMarker(mv);
        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.animateY(700);

        for (IDataSet set : mChart.getData().getDataSets()){
            set.setDrawValues(false);
        }


    }


    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 7; i++) {
            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "考勤统计");
        d.setColors(Color.GREEN);
        d.setHighLightAlpha(125);
        BarData cd = new BarData(d);

        cd.setBarWidth(0.9f);

        return cd;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_group_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
