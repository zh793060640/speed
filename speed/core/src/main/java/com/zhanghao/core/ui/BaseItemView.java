package com.zhanghao.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanghao.core.R;

/**
 * 作者： zhanghao on 2017/12/26.
 * 功能：${des}
 */

public class BaseItemView extends LinearLayout {

    private TextView tvName,tv_item_right;
    private ImageView imgIcon, imgArrow;
    private View lineSpilt;
    private Boolean showArrow, showLine;

    public BaseItemView(Context context) {
        this(context, null);
    }

    public BaseItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseItemView);
        String name = ta.getString(R.styleable.BaseItemView_ItemName);
        showArrow = ta.getBoolean(R.styleable.BaseItemView_arrow_visibility, true);
        showLine = ta.getBoolean(R.styleable.BaseItemView_spilt_visibility, true);
        Drawable image = ta.getDrawable(R.styleable.BaseItemView_ItemImage);
        ta.recycle();
        LayoutInflater.from(context).inflate(R.layout.layout_baseitem_view, this);
        tvName = (TextView) findViewById(R.id.tv_item_name);
        tv_item_right = (TextView) findViewById(R.id.tv_item_right);
        imgIcon = (ImageView) findViewById(R.id.img_item_icon);
        imgArrow = (ImageView) findViewById(R.id.img_item_arrow);
        lineSpilt = findViewById(R.id.item_line);
        if (image != null) {
            imgIcon.setImageDrawable(image);
        }else {
            imgIcon.setVisibility(View.GONE);
        }
        tvName.setText(name);

        initLineArrow();
    }

    private void initLineArrow() {
        if (showArrow) {
            imgArrow.setVisibility(View.VISIBLE);
        } else {
            imgArrow.setVisibility(View.INVISIBLE);
        }

        if (showLine) {
            lineSpilt.setVisibility(View.VISIBLE);
        } else {
            lineSpilt.setVisibility(View.INVISIBLE);
        }
    }

    public void setShowArrow(Boolean showArrow) {
        this.showArrow = showArrow;
        initLineArrow();
    }

    public void setShowLine(Boolean showLine) {
        this.showLine = showLine;
        initLineArrow();
    }

    public void setItemName(String name) {
        tvName.setText(name);
    }

    public void setRightText (String text){
        tv_item_right.setText(text);
    }

    public void setRightText (String text,int drawableLeft){

        Drawable left= getResources().getDrawable(drawableLeft);
        left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight()); //设置边界
        tv_item_right.setCompoundDrawablePadding(10);
        tv_item_right.setCompoundDrawables(left,null,null,null);
        tv_item_right.setText(text);
    }
}
