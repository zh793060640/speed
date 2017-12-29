package com.zhanghao.core.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CommentDialog extends DialogFragment {
    public static final String TAG = "CommentDialog";
    public static final String HINT_CONTENT = "hint_content";
    private EditText edtComment;
    private LinearLayout llInput;
    private Button btSend;
    private ImageView imgEmoji;
    private String content;
    private Context mContext;
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.layout_comment_dialog, container, false);
        edtComment = (EditText) view.findViewById(R.id.edt_comment);
        llInput = (LinearLayout) view.findViewById(R.id.ll_input);
        btSend = (Button) view.findViewById(R.id.bt_send);
        imgEmoji = (ImageView) view.findViewById(R.id.img_emoji);
        mContext = getActivity();

        KeyboardUtils.openKeybord(edtComment, mContext);
//点击返回键关闭dialog和输入框
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    KeyboardUtils.closeKeybord(edtComment, mContext);
                    dismiss();
                    // KeyboardUtils.TimerHideKeyboard(edtComment);
                    return true;
                } else {
                    return false;
                }
            }

        });

        edtComment.addTextChangedListener(new CommonTextWatch() {
            @Override
            public void afterTextChanged(Editable s) {
                content = edtComment.getText().toString();

                if (EmptyUtils.isEmpty(content)) {
                    btSend.setBackgroundResource(R.drawable.shape_input);
                    btSend.setTextColor(getActivity().getResources().getColor(R.color.text_color));
                    btSend.setEnabled(false);
                } else {
                    ShapeUtils.setShape(Color.parseColor("#FF9000"), 5, btSend);
                    btSend.setTextColor(getActivity().getResources().getColor(R.color.white));
                    btSend.setEnabled(true);
                }
            }
        });
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentListener != null) {
                    InputDismiss();
                    dismiss();
                    commentListener.send(content);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setCanceledOnTouchOutside(true);
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        lp.x = 0;
        lp.y = 0;
        lp.width = lp.MATCH_PARENT;
        lp.height = lp.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        KeyboardUtils.TimerHideKeyboard(edtComment);

    }


    //输入框关闭后的处理
    private void InputDismiss() {
        KeyboardUtils.closeKeybord(edtComment, mContext);
    }

    private CommentListener commentListener;

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    public interface CommentListener {
        public void send(String content);
    }

}
