package com.zhanghao.speed.temp;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.zhanghao.core.base.BaseActivity;
import com.zhanghao.core.ui.CircleProgressView;
import com.zhanghao.speed.R;
import com.zhanghao.speed.mvp.MainContract;
import com.zhanghao.speed.mvp.MainModel;
import com.zhanghao.speed.mvp.MainPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 作者： zhanghao on 2018/1/29.
 * 功能：${des}
 */

public class DateCustomeActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {
    MonthPager monthPager;
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarDate currentDate;
    private LinearLayout choose_date_view;
    CircleProgressView circleProgressView;
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        monthPager = (MonthPager) findViewById(R.id.calendar_view);
        circleProgressView = (CircleProgressView) findViewById(R.id.circleProgressView);
        circleProgressView.setProgress(30);
        choose_date_view = (LinearLayout) findViewById(R.id.choose_date_view);
        //此处强行setViewHeight，毕竟你知道你的日历牌的高度
        monthPager.setViewHeight(DensityUtil.dp2px(270));
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {

            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
        CustomDayView customDayView = new CustomDayView(activity, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                activity,
                onSelectDateListener,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Sunday,
                customDayView);
        initMarkData();
        initMonthPager();
        choose_date_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickUtils.showPickView(activity, new DatePickUtils.DatePickListener() {
                    @Override
                    public void onPickate(String time, Date date) {
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        c.setTime(date);
                        CalendarDate today = new CalendarDate(c.get(java.util.Calendar.YEAR), c.get(java.util.Calendar.MONTH) + 1, c.get(java.util.Calendar.DATE));
                        initMarkData();
                        calendarAdapter.notifyDataChanged(today);
                    }
                });
            }
        });
    }

    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2017-8-9", "1");
        markData.put("2017-7-9", "0");
        markData.put("2017-6-9", "1");
        markData.put("2017-6-10", "0");
        markData.put("2018-1-15", "0");
        markData.put("2018-1-1", "0");
        markData.put("2018-1-2", "0");
        markData.put("2018-1-16", "0");
        calendarAdapter.setMarkData(markData);
    }

    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
//                    currentDate = date;
//                    tvYear.setText(date.getYear() + "年");
//                    tvMonth.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void initData() {

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_syllabus;
    }
}
