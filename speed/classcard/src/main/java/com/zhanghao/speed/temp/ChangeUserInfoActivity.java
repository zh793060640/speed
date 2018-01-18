package com.zhanghao.speed.temp;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zhanghao.core.base.BaseActivity;
import com.zhanghao.core.utils.EmptyUtils;
import com.zhanghao.speed.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： zhanghao on 2018/1/18.
 * 功能：${des}
 */

public class ChangeUserInfoActivity extends BaseActivity {

    public static final String HINT_TAG = "hint";
    public static final String TITLE_TAG = "title";
    public static final String CONTENT_TAG = "content";
    public static final String TYPE_TAG = "type";
    public static final String PARAM_TYPE = "param_type";
    public static final int TEXT = 0;
    public static final int SEX = 1;

    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.recyclerView_sex)
    RecyclerView recyclerViewSex;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    private String hint = "";
    private String title = "";
    private String content = "";
    private String param = "";
    private int type = 0;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("TYPE_TAG", 0);
        if (type == 1) {
            recyclerViewSex.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);
        }
        title = getIntent().getStringExtra(TITLE_TAG);
        hint = getIntent().getStringExtra(HINT_TAG);
        content = getIntent().getStringExtra(CONTENT_TAG);
        param = getIntent().getStringExtra(PARAM_TYPE);
        edtContent.setHint(hint);
        if (!EmptyUtils.isEmpty(content)) {
            edtContent.setText(content);
            edtContent.setSelection(content.length());
        }
        myTitleBar.setTitle(title);
        myTitleBar.setRight("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_change_userinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
