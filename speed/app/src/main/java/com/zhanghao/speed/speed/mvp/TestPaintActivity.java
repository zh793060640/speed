package com.zhanghao.speed.speed.mvp;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.zhanghao.speed.speed.R;
import com.zhanghao.speed.speed.base.BaseActivity;
import com.zhanghao.speed.speed.widget.CircleMenuLayout;

/**
 * Created by PC on 2017/3/3.
 * 作者 ：张浩
 * 作用：
 */

public class TestPaintActivity extends BaseActivity {
    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts = new String[] { "安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡" };
    private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal };

    @Override
    protected void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_testpain;
    }

    @Override
    public void initViewsAndListener() {
        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);



        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
        {

            @Override
            public void itemClick(View view, int pos)
            {
                Toast.makeText(TestPaintActivity.this, mItemTexts[pos],
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void itemCenterClick(View view)
            {
                Toast.makeText(TestPaintActivity.this,
                        "you can do something just like ccb  ",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initToolbar() {
        StatusBarUtil.setColor(this, Color.parseColor("#8B8B83"));
    }

    @Override
    public void close() {

    }
}
