package com.zhanghao.speed;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.timchat.ui.HomeActivity;
import com.tencent.qcloud.timchat.ui.customview.DialogActivity;
import com.tencent.qcloud.timchat.utils.PushUtil;
import com.tencent.qcloud.ui.NotifyDialog;
import com.zhanghao.core.base.BaseActivity;
import com.zhanghao.core.base.BaseWebActivity;
import com.zhanghao.core.ui.BaseRefreshView;
import com.zhanghao.core.ui.CommentDialgNew;
import com.zhanghao.core.ui.CommentDialog;
import com.zhanghao.core.utils.GalleryFinalUtils;
import com.zhanghao.core.zbar.ZbarActivity;
import com.zhanghao.speed.mvp.MainContract;
import com.zhanghao.speed.mvp.MainModel;
import com.zhanghao.speed.mvp.MainPresenter;
import com.zhanghao.speed.test.CoordinatorLayoutActivity;
import com.zhanghao.speed.test.DragSortActivity;
import com.zhanghao.speed.test.StickActivity;
import com.zhanghao.speed.test.TestAdapter;
import com.zhanghao.speed.test.TransationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;


public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View, GalleryFinal.OnHanlderResultCallback {


    @BindView(R.id.tvShape)
    TextView tvShape;
    @BindView(R.id.baseRefreshView)
    BaseRefreshView baseRefreshView;
//    private BaseRefreshView baseRefreshView;
//    TextView tvShape;

    private CommentDialgNew commentDialog;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //激活过度元素
        super.onCreate(savedInstanceState);
        InitBusiness.start(getApplicationContext(), TIMLogLevel.DEBUG.ordinal());
        initTIMUserConfig();
        intervelHX();
    }

    @Override
    public boolean isShowBaseTitle() {
        return false;
    }

    @Override
    protected void initView() {
        // myTitleBar.setTitle("测试");
//        baseRefreshView = findView(R.id.baseRefreshView);
//        tvShape = findView(R.id.tvShape);

        List<String> data = new ArrayList<>();
        data.add("图片选择");
        data.add("二维码");
        data.add("5.0转场动画");
        data.add("仿微信图片查看");
        data.add("x5webview");
        data.add("CoordinatorLayout");
        data.add("吸顶效果");
        data.add("拖拽排序");
        data.add("评论框");
        data.add("腾讯im");
        //FragmentUtils.addFragment(fm, new TestFragment(), R.id.fragment);
        baseRefreshView.setRefreshListener(new BaseRefreshView.RefreshListener() {

            @Override
            public void refresh() {
                baseRefreshView.setRefreshAndLoadFinish();
            }

            @Override
            public void loadMore(int page) {
                baseRefreshView.setRefreshAndLoadFinish();
            }
        });

        TestAdapter adapter = new TestAdapter(activity, data);
        baseRefreshView.setAdapter(adapter);
        adapter.setListener(new TestAdapter.OnItemClickListener() {
            @Override
            public void onclick(int postion) {
                int a = postion;
                switch (a) {
                    case 0:
                        GalleryFinalUtils.openGalleryMuti(9, MainActivity.this);
                        break;
                    case 1:
                        Intent intent = new Intent(activity, ZbarActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //startActivity(new Intent(activity, TransationActivity.class), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                        startActivity(new Intent(activity, TransationActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(activity, TransationActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(activity, BaseWebActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(activity, CoordinatorLayoutActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(activity, StickActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(activity, DragSortActivity.class));
                    case 8:
                        showCommentDialog("回复张浩：");
                        break;
                    case 9:

                        break;
                }
            }
        });

    }



    public void loginTX(){

    }
    @Override
    protected void initData() {
        mPresenter.getInfo();
    }


    @Override
    protected int getContentView() {

        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
        if (resultList != null) {
            mPresenter.uploadPhoto(resultList.get(0).getPhotoPath());
        }
    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {

    }

    public void showCommentDialog(String hint) {
        commentDialog = new CommentDialgNew(activity, hint);
        commentDialog.setCommentListener(new CommentDialog.CommentListener() {
            @Override
            public void send(String content) {

            }
        });
        commentDialog.show();
    }

    private void initTIMUserConfig() {
        //登录之前要初始化群和好友关系链缓存
        TIMUserConfig userConfig = new TIMUserConfig();
        userConfig.setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                Log.d(TAG, "receive force offline message");
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(intent);
            }

            @Override
            public void onUserSigExpired() {
                //票据过期，需要重新登录
                new NotifyDialog().show(getString(com.tencent.qcloud.timchat.R.string.tls_expire), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                            logout();
                    }
                });
            }
        })
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i(TAG, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(TAG, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(TAG, "onWifiNeedAuth");
                    }
                });

        //设置刷新监听
        RefreshEvent.getInstance().init(userConfig);
        userConfig = FriendshipEvent.getInstance().init(userConfig);
        userConfig = GroupEvent.getInstance().init(userConfig);
        userConfig = MessageEvent.getInstance().init(userConfig);
        TIMManager.getInstance().setUserConfig(userConfig);


    }


    //循环检查环信初始化
    private void intervelHX() {
        String user = "lwt70009";
        String sign = "eJxlz0FPgzAUwPE7n6LhbFwpIGOJh4lThjOFbJjghSC0zVPHsFShGr*7yJZIYq--X957-TIQQuZusz0vyvLwXqtc6YaZaIFMbJ79xaaBKi9UbsvqX2R9A5LlBVdMjtFyXZdgPDVQsVoBh5N47ZQ3AH8i2uolH9ccRzhDdn3Hs6cExBjvV2mwToKL6DPU7bMWybKELHpapgQqDOl2JcQegozFodVzrQOarEWY3CmqH2PZKZ49RMPNV7NNwewb*rbrZjhO6*ue39qUsvnlZKWCPTv9yXJ8z5oTZ1I-mGzhUI*A4IEQG-8*0-g2fgBU4F55";

        LoginBusiness.loginIm(user, sign, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "login error : code " + i + " " + s);
                switch (i) {
                    case 6208:
                        //离线状态下被其他终端踢下线
                        NotifyDialog dialog = new NotifyDialog();
                        dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initTIMUserConfig();
                            }
                        });
                        break;
                    case 6200:
                        Toast.makeText(MainActivity.this, getString(R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
                        initTIMUserConfig();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                        initTIMUserConfig();
                        break;
                }
            }

            @Override
            public void onSuccess() {
                //初始化程序后台后消息推送
                PushUtil.getInstance();
                //初始化消息监
                MessageEvent.getInstance();
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });


    }
}
