package com.zhanghao.core.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanghao.core.R;
import com.zhanghao.core.utils.CommonTextWatch;
import com.zhanghao.core.utils.EmptyUtils;

/**
 * 作者： zhanghao on 2018/1/2.
 * 功能：${des}
 */

public class CustomEditext extends LinearLayout {
    private ImageView imgLeft, imgRight;
    private EditText edtContent;
    private LinearLayout ll_parent;
    Boolean showRight;
    public CustomEditext(Context context) {
        this(context, null);
    }

    public CustomEditext(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditext(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomEditext);

        String hint = ta.getString(R.styleable.CustomEditext_hint);
        Drawable leftimage = ta.getDrawable(R.styleable.CustomEditext_leftImage);
        Drawable rightimage = ta.getDrawable(R.styleable.CustomEditext_rightImage);
        Drawable background = ta.getDrawable(R.styleable.CustomEditext_background);
        Boolean showLeft = ta.getBoolean(R.styleable.CustomEditext_showLeft, true);
        showRight = ta.getBoolean(R.styleable.CustomEditext_showRight, true);
        ta.recycle();

        LayoutInflater.from(context).inflate(R.layout.layout_custom_editext, this);
        imgLeft = (ImageView) findViewById(R.id.imgLeft);
        imgRight = (ImageView) findViewById(R.id.imgRight);
        edtContent = (EditText) findViewById(R.id.edtContent);
        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);
        edtContent.setHint(hint);

        if (leftimage != null) {
            imgLeft.setImageDrawable(leftimage);
        }

        if (rightimage != null) {
            imgRight.setImageDrawable(rightimage);
        }
        if (background != null) {
            ll_parent.setBackground(background);
        }

        if (showLeft) {
            imgLeft.setVisibility(View.VISIBLE);
        } else {
            imgLeft.setVisibility(View.INVISIBLE);
        }


        imgRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edtContent.setText("");
            }
        });

        edtContent.addTextChangedListener(new CommonTextWatch() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = edtContent.getText().toString();
                if (!EmptyUtils.isEmpty(content)&&showRight) {
                    imgRight.setVisibility(View.VISIBLE);
                } else {
                    imgRight.setVisibility(View.GONE);
                }

            }
        });
        edtContent.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (edtContent.getText().toString().length() > 0) {
                    //用户有输入内容
                    if (hasFocus&&showRight) {
                        imgRight.setVisibility(View.VISIBLE);
                    } else {
                        imgRight.setVisibility(View.GONE);
                    }
                }
            }
        });

        edtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (actionListener != null) {
                        actionListener.actionDo();
                    }
                }
                return false;
            }
        });

    }

    public void setEditActionDo(Boolean temp) {
        if (temp) {
        edtContent.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
    }

    public String getContentText() {
        return edtContent.getText().toString();
    }


    private ActionListener actionListener;

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {
        void actionDo();
    }

}
