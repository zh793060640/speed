package com.zhanghao.speed.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhanghao.speed.R;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class StickActivity extends AppCompatActivity {

    private StickyListHeadersListView stickView;
    StickAdapter stickAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick);
        stickView = (StickyListHeadersListView) findViewById(R.id.stickView);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add("fragment_test" + i);
        }
        stickAdapter = new StickAdapter(this, R.layout.item_test, data);
        stickView.setAdapter(stickAdapter);
    }
}
