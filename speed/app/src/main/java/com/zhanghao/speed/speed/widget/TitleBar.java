package com.zhanghao.speed.speed.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhanghao.speed.speed.R;

import butterknife.BindView;

/**
 * Created by PC on 2017/3/10.
 * 作者 ：张浩
 * 作用：
 */

public class TitleBar extends FrameLayout {


    @BindView(R.id.imgLeft)
    ImageView mImgLeft;
    @BindView(R.id.tvLeft)
    TextView mTvLeft;
    @BindView(R.id.imgCenter)
    ImageView mImgCenter;
    @BindView(R.id.tvCenter)
    TextView mTvCenter;
    @BindView(R.id.imgRight)
    ImageView mImgRight;
    @BindView(R.id.tvRight)
    TextView mTvRight;

    public TitleBar(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_titlebar, null);
        addView(view);
    }
}
