package com.zhanghao.speed.temp;

import android.view.View;

import com.bumptech.glide.Glide;
import com.zhanghao.core.base.BaseActivity;
import com.zhanghao.core.ui.BaseItemView;
import com.zhanghao.core.utils.ACache;
import com.zhanghao.core.utils.FileUtils;
import com.zhanghao.speed.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： zhanghao on 2018/1/18.
 * 功能：${des}
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.change_password)
    BaseItemView changePassword;
    @BindView(R.id.msg_setting)
    BaseItemView msgSetting;
    @BindView(R.id.clear_cache)
    BaseItemView clearCache;
    @BindView(R.id.about_me)
    BaseItemView aboutMe;
    @BindView(R.id.help)
    BaseItemView help;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        aboutMe.setRightText("v1.0.0");
        clearCache.setRightText(getCacheSize());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.change_password, R.id.msg_setting, R.id.clear_cache, R.id.about_me, R.id.help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_password:
                break;
            case R.id.msg_setting:
                break;
            case R.id.clear_cache:
                ACache.get(activity).clear();
                clearCache.setRightText("暂无缓存");
                break;
            case R.id.about_me:
                break;
            case R.id.help:
                break;
        }
    }

    public String getCacheSize() {
        double cache = 0;
        try {
            cache = FileUtils.getFileOrFilesSize(FileUtils.getBaseCacheFile(), FileUtils.SIZETYPE_B);
            cache += FileUtils.getFileOrFilesSize(Glide.getPhotoCacheDir(activity), FileUtils.SIZETYPE_B);
        } catch (Exception e) {
        } finally {
            String size = FileUtils.FormetFileSize(cache);
            return size;
        }
    }
}
