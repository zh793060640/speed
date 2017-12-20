package com.zhanghao.core.imagepreview;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.previewlibrary.GPreviewActivity;
import com.zhanghao.core.R;

/**
 * Created by yangc on 2017/9/19.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */

public class PhtotoActivity extends GPreviewActivity {


    /***
     * 重复该方法
     * 使用你的自定义布局
     **/
    @Override
    public int setContentLayout() {
        return R.layout.activity_custom_preview;
    }

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出时调用，d封装方法的 不然没有动画效果
                transformOut();
            }
        });
    }
}
