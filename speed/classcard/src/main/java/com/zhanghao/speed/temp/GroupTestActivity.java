package com.zhanghao.speed.temp;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhanghao.core.base.BaseNormalActivity;
import com.zhanghao.speed.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： zhanghao on 2018/2/5.
 * 功能：${des}
 */

public class GroupTestActivity extends BaseNormalActivity {
    @BindView(R.id.test_group)
    ExpandableListView testGroup;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private HashMap<String, List<SectionInfo>> data = new HashMap<>();
    private List<String> group = new ArrayList<>();
    TestGroupAdapter testGroupAdapter;

    @Override
    protected void initView() {

        for (int i = 0; i < 5; i++) {
            group.add("group" + i);
            List<SectionInfo> temp = new ArrayList<>();

            for (int i1 = 0; i1 < 8; i1++) {
                SectionInfo info = new SectionInfo();
                info.name = "child"+i1;
                temp.add(info);
            }
            data.put("group" + i, temp);
        }

        testGroupAdapter = new TestGroupAdapter(activity, group, data);
        testGroup.setAdapter(testGroupAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_group_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
