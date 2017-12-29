package com.zhanghao.core.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhanghao.core.R;
import com.zhanghao.core.utils.CommonTextWatch;
import com.zhanghao.core.utils.EmptyUtils;
import com.zhanghao.core.utils.KeyboardUtils;
import com.zhanghao.core.utils.ShapeUtils;

/**
 * 作者： zhanghao on 2017/12/29.
 * 功能：${des}
 */

public class CommentDialgNew extends Dialog {
    public static final String HINT_CONTENT = "hint_content";
    private String content;
    private String hint;
    private Context mContext;
    private EditText edtComment;
    private LinearLayout llInput;
    private Button btSend;
    private ImageView imgEmoji;

    public CommentDialgNew(@NonNull Context context, String hint) {
        super(context);
        this.mContext = context;
        this.hint = hint;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置为居中
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_comment_dialog, null);
        edtComment = (EditText) view.findViewById(R.id.edt_comment);
        llInput = (LinearLayout) view.findViewById(R.id.ll_input);
        btSend = (Button) view.findViewById(R.id.bt_send);
        imgEmoji = (ImageView) view.findViewById(R.id.img_emoji);
        setContentView(view);
        KeyboardUtils.openKeybord(edtComment, mContext);
        edtComment.addTextChangedListener(new CommonTextWatch() {
            @Override
            public void afterTextChanged(Editable s) {
                content = edtComment.getText().toString();

                if (EmptyUtils.isEmpty(content)) {
                    btSend.setBackgroundResource(R.drawable.shape_input);
                    btSend.setTextColor(mContext.getResources().getColor(R.color.text_color));
                    btSend.setEnabled(false);
                } else {
                    ShapeUtils.setShape(Color.parseColor("#FF9000"), 5, btSend);
                    btSend.setTextColor(mContext.getResources().getColor(R.color.white));
                    btSend.setEnabled(true);
                }
            }
        });
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentListener != null) {
                    dismiss();
                    KeyboardUtils.closeKeybord(edtComment, mContext);
                    commentListener.send(content);
                }
            }
        });

        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    KeyboardUtils.closeKeybord(edtComment, mContext);
                    return true;
                } else {
                    return false;
                }
            }
        });
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isShowing() && shouldCloseOnTouch(getContext(), event)) {
            KeyboardUtils.closeKeybord(edtComment, mContext);
        }
        return super.onTouchEvent(event);
    }

    public boolean shouldCloseOnTouch(Context context, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && isOutOfBounds(context, event) && getWindow().peekDecorView() != null) {
            return true;
        }
        return false;
    }

    private boolean isOutOfBounds(Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        return (x < -slop) || (y < -slop)
                || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }


    private CommentDialog.CommentListener commentListener;

    public void setCommentListener(CommentDialog.CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    public interface CommentListener {
        public void send(String content);
    }
}
