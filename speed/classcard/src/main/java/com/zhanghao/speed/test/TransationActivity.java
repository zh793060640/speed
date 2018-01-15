package com.zhanghao.speed.test;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.previewlibrary.enitity.ThumbViewInfo;
import com.zhanghao.core.base.BaseActivity;
import com.zhanghao.core.ui.CustomEditext;
import com.zhanghao.core.utils.ToastUtils;
import com.zhanghao.speed.R;
import com.zhanghao.speed.mvp.MainModel;
import com.zhanghao.speed.mvp.MainPresenter;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： zhanghao on 2017/10/9.
 * 功能：${des}
 */

public class TransationActivity extends BaseActivity<MainPresenter, MainModel> {
    private RecyclerView recyclerView;
    private CustomEditext edt1, edt2;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();

    private NiceSpinner nice_spinner;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //激活过度元素
//        getWindow().setEnterTransition(new Fade().setDuration(1000));
//        getWindow().setExitTransition(new Fade().setDuration(1000));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        myTitleBar.setTitle("转场动画");
        recyclerView = findView(R.id.recyclerView);
        nice_spinner = findView(R.id.nice_spinner);
        edt1 = findView(R.id.edt1);
        edt2 = findView(R.id.edt2);
        edt2.setEditActionDo(true);
        edt2.setActionListener(new CustomEditext.ActionListener() {
            @Override
            public void actionDo() {
                ToastUtils.showLongToast(edt2.getContentText());
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        nice_spinner.attachDataSource(dataset);
        nice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + dataset.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> urls = ImageUrlConfig.getUrls();
        for (int i = 0; i < 9; i++) {
            mThumbViewInfoList.add(new ThumbViewInfo(urls.get(i)));
        }
        NineAdapter adapter = new NineAdapter(activity, R.layout.item_nineimage, mThumbViewInfoList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_transation;
    }
}
