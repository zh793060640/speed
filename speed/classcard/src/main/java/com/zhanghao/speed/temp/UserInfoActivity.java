package com.zhanghao.speed.temp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhanghao.core.base.BaseActivity;
import com.zhanghao.core.ui.BaseItemView;
import com.zhanghao.core.utils.LogUtils;
import com.zhanghao.speed.R;
import com.zhanghao.speed.mvp.MainContract;
import com.zhanghao.speed.mvp.MainModel;
import com.zhanghao.speed.mvp.MainPresenter;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： zhanghao on 2018/1/18.
 * 功能：${des}
 */

public class UserInfoActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.username)
    BaseItemView username;
    @BindView(R.id.user_hone)
    BaseItemView userHone;
    @BindView(R.id.user_realname)
    BaseItemView userRealname;
    @BindView(R.id.user_sex)
    BaseItemView userSex;
    @BindView(R.id.user_nickname)
    BaseItemView userNickname;
    @BindView(R.id.user_birthday)
    BaseItemView userBirthday;
    @BindView(R.id.user_intrest)
    BaseItemView userIntrest;
    @BindView(R.id.user_wexin)
    BaseItemView userWexin;
    @BindView(R.id.user_qq)
    BaseItemView userQq;


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_head, R.id.username, R.id.user_hone, R.id.user_realname, R.id.user_sex, R.id.user_nickname, R.id.user_birthday, R.id.user_intrest, R.id.user_wexin, R.id.user_qq})
    public void onViewClicked(View view) {
        Intent intent = new Intent(activity, ChangeUserInfoActivity.class);
        String title = "";
        String hint = "";
        String content = "";
        int type = 0;
        String param = "";
        switch (view.getId()) {

            case R.id.ll_head:

                return;
            case R.id.username:
                title = "修改用户名";
                hint = "请输入用户名";
                content = "";
                param = "";
                break;
            case R.id.user_hone:
                title = "修改用户名";
                hint = "请输入用户名";
                content = "";
                param = "";
                break;
            case R.id.user_realname:
                title = "修改真实姓名";
                hint = "请输入真实姓名";
                content = "";
                param = "";
                break;
            case R.id.user_sex:
                return;
            case R.id.user_nickname:
                title = "修改昵称";
                hint = "请输入昵称";
                content = "";
                param = "";
                break;
            case R.id.user_birthday:
                DatePickUtils.showPickView(activity, new DatePickUtils.DatePickListener() {
                    @Override
                    public void onPickate(String time, Date date) {
                        LogUtils.d(time);
                    }
                });
                return;
            case R.id.user_intrest:
                title = "修改兴趣爱好";
                hint = "请输入兴趣爱好";
                content = "";
                param = "";
                break;
            case R.id.user_wexin:
                title = "修改微信号";
                hint = "请输入微信号";
                content = "";
                param = "";
                break;
            case R.id.user_qq:
                title = "修改QQ号";
                hint = "请输入QQ号";
                content = "";
                param = "";
                break;
        }

        intent.putExtra(ChangeUserInfoActivity.TITLE_TAG, title);
        intent.putExtra(ChangeUserInfoActivity.HINT_TAG, hint);
        intent.putExtra(ChangeUserInfoActivity.TYPE_TAG, type);
        intent.putExtra(ChangeUserInfoActivity.PARAM_TYPE, param);
        intent.putExtra(ChangeUserInfoActivity.CONTENT_TAG, content);
    }
}
