package com.leeone.core.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leeone.core.R;


public class MyTitleBar extends FrameLayout {

    private ImageView imgLeft, imgRight;
    private TextView tvTitle, tvRight;
    private LinearLayout llLeft;
    private LinearLayout llRight;

    public MyTitleBar(@NonNull Context context) {
        this(context, null);
    }

    public MyTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleBar(final @NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_titlebar, null);
        imgLeft = (ImageView) view.findViewById(R.id.imgLeft);
        imgRight = (ImageView) view.findViewById(R.id.head_rightimage);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvRight = (TextView) view.findViewById(R.id.tvRight);
        llLeft = (LinearLayout) view.findViewById(R.id.head_leftshow);
        llRight = (LinearLayout) view.findViewById(R.id.head_rightshow);
        llLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
            }
        });
        addView(view);
    }

    public void setImgLeft(int Left, OnClickListener listener) {
        imgLeft.setImageResource(Left);
        llLeft.setOnClickListener(listener);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setRight(String right, OnClickListener listener) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(right);
        tvRight.setOnClickListener(listener);
    }

    public void setRight(int right, OnClickListener listener) {
        imgRight.setVisibility(VISIBLE);
        imgRight.setImageResource(right);
        imgRight.setOnClickListener(listener);
    }

    public void setRight(String right) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(right);
    }
}
