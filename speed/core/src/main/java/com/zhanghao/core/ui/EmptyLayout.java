package com.zhanghao.core.ui;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhanghao.core.R;

public class EmptyLayout extends FrameLayout {

    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int HIDE_LAYOUT = 4;
    private boolean clickEnable = true;
    private final Context context;
    public ImageView imgError;
    public ImageView imgLoading;
    private TextView tvLoadingText;
    private RelativeLayout mLayout;
    private int mErrorState;
    private String strNoDataContent = "";
    private String loadingText = "";
    private int noNetworkImage;
    private int nodataImage;
    private AnimationDrawable animationDrawable;

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        strNoDataContent = "暂无数据";
        loadingText = "加载中...";
        noNetworkImage = R.drawable.file_icon_default;
        nodataImage = R.drawable.empty_icon;
        View view = View.inflate(context, R.layout.view_error_layout, null);
        imgError = (ImageView) view.findViewById(R.id.img_error_layout);
        imgLoading = (ImageView) view.findViewById(R.id.img_Loading);
        tvLoadingText = (TextView) view.findViewById(R.id.tv_error_layout);
        mLayout = (RelativeLayout) view.findViewById(R.id.pageerrLayout);

        mLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    if (mOnEmptyClickListener == null) {
                        return;
                    }
                    switch (mErrorState) {
                        case NETWORK_ERROR:
                            mOnEmptyClickListener.netWorkErrorClick();
                            break;
                        case NODATA:
                            mOnEmptyClickListener.emptyDataClick();
                            break;
                    }

                }
            }
        });
        //imgLoading.setBackgroundResource(R.drawable.refresh_header);
//        animationDrawable = (AnimationDrawable) imgLoading.getDrawable();
//        animationDrawable.start();
        addView(view);
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    public void setErrorMessage(String msg) {
        tvLoadingText.setText(msg);
    }

    public void setErrorImag(int imgResource) {
        try {
            imgLoading.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                imgLoading.setVisibility(View.GONE);
                imgError.setBackgroundResource(noNetworkImage);
                imgError.setVisibility(View.VISIBLE);
                tvLoadingText.setText("网络异常，点击重新加载");
                tvLoadingText.setVisibility(VISIBLE);
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                imgError.setVisibility(View.GONE);
                imgLoading.setVisibility(View.VISIBLE);
                tvLoadingText.setText(loadingText);
                tvLoadingText.setVisibility(VISIBLE);
                animationDrawable.start();
                clickEnable = false;
                break;
            case NODATA:
                mErrorState = NODATA;
                imgLoading.setVisibility(View.GONE);
                imgError.setBackgroundResource(nodataImage);
                imgError.setVisibility(View.VISIBLE);
                setTvNoDataContent();
                tvLoadingText.setVisibility(VISIBLE);
                clickEnable = true;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                if (animationDrawable != null) {
                    animationDrawable.stop();
                }
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setNoDataImage(int noDataImage) {
        this.nodataImage = noDataImage;
    }

    public void setTvNoDataContent() {
        if (!strNoDataContent.equals(""))
            tvLoadingText.setText(strNoDataContent);
        else
            tvLoadingText.setText(loadingText);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }

    @Override
    protected void onLayout(boolean arg0, int l, int t, int r, int b) {

        int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = this.getChildAt(i);
            child.setVisibility(View.VISIBLE);
            //child.measure(r-l, b-t);
            int x = l;
            int y = t;
//心中疑惑：我认为我只要在这里调用child的layout(int,int,int,int)方法给出它的位置和宽高就可以了
//如果child本身是一个ViewGroup的话它是否应该自己去管理它本身内部的child的位置和宽度呢???
            child.layout(x, y, x + getWidth(), y + getHeight());
        }
    }

//    public void initAnim() {
//        animationDrawable = new AnimationDrawable();
//        File animFile = FileUtils.getRefreshPath("loading");
//        int num = animFile.list().length;
//        for (int i = 0; i < num; i++) {
//            File temp = FileUtils.getRefreshOnePath("loading", i + "");
//            if (temp.exists()) {
//                try {
//                    Bitmap one = BitmapFactory.decodeStream(new FileInputStream(temp));
//                    BitmapDrawable drawble = new BitmapDrawable(one);
//                    animationDrawable.addFrame(drawble, 200);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                continue;
//            }
//        }
//
//        animationDrawable.setOneShot(false);
//        imgLoading.setImageDrawable(animationDrawable);
//        animationDrawable.start();
//    }

    public void setOnEmptyClickListener(OnEmptyClickListener onEmptyClickListener) {
        mOnEmptyClickListener = onEmptyClickListener;
    }

    private OnEmptyClickListener mOnEmptyClickListener;

    public interface OnEmptyClickListener {
        void netWorkErrorClick();

        void emptyDataClick();
    }
}
