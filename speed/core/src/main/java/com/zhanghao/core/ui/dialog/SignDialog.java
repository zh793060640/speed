package com.zhanghao.core.ui.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhanghao.core.R;
import com.zhanghao.core.utils.FileUtils;
import com.zhanghao.core.utils.ToastUtils;

import java.io.IOException;

/**
 * 作者： zhanghao on 2017/7/31.
 * 功能：${des}
 */

public class SignDialog extends DialogFragment {

    public final static String TAG = "SignDialog";
    private ImageView imgArrow;
    private LinePathView signView;
    private SignListener signListener;
    private TextView tvSave, tvClear;
    private LinearLayout llSave, llClear, llPaint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_sign, null);
        imgArrow = (ImageView) view.findViewById(R.id.imgDissmiss);
        signView = (LinePathView) view.findViewById(R.id.signView);
        signView.setPenColor(getResources().getColor(R.color.colorAccent));
        tvSave = (TextView) view.findViewById(R.id.tvSave);
        tvClear = (TextView) view.findViewById(R.id.tvClear);

        llSave = (LinearLayout) view.findViewById(R.id.llSave);
        llClear = (LinearLayout) view.findViewById(R.id.llClear);
        llPaint = (LinearLayout) view.findViewById(R.id.llPaint);


        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        llClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                } else {
                    return false;
                }
            }

        });

        return view;
    }

    public void clear() {
        signView.clear();
    }

    public void save() {
        if (signView.getTouched()) {
            try {
                //  String path = FileUtils.getCacheFilePath("sign").getAbsolutePath() + ".png";
                String path = FileUtils.getImageCachePath(getActivity()).getAbsolutePath() + "sign.png";
                signView.save(path, true, 10);
                if (signListener != null) {
                    signListener.saveSuccess(path);
                }
                dismiss();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showLongToast("您没有签名");
        }
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
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }

    public SignListener getSignListener() {
        return signListener;
    }

    public void setSignListener(SignListener signListener) {
        this.signListener = signListener;
    }

    public interface SignListener {
        void saveSuccess(String path);
    }
}
