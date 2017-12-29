package com.zhanghao.speed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
                }
            }
        });

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
}
