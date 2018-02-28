package com.zhanghao.speed.test;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.zhanghao.core.base.BaseNormalActivity;
import com.zhanghao.speed.R;

/**
 * 作者： zhanghao on 2018/2/28.
 * 功能：${des}
 */

public class GalleryActivity extends BaseNormalActivity {
    private ViewPager mViewPager;
    private RelativeLayout mViewPagerContainer;
    private static int TOTAL_COUNT = 10;
    private TextView tvNum;

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tvNum = (TextView) findViewById(R.id.tv_num);
        mViewPagerContainer = (RelativeLayout) findViewById(R.id.viewPagerContainer);
        initViewPager();
    }

    private void initViewPager() {
        //设置ViewPager的布局
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                DensityUtil.dp2px(200),
                DensityUtil.dp2px(300));


        /**** 重要部分  ******/
        //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
        mViewPager.setClipChildren(false);
        //父容器一定要设置这个，否则看不出效果
        mViewPagerContainer.setClipChildren(false);


        mViewPager.setLayoutParams(params);
        //为ViewPager设置PagerAdapter
        mViewPager.setAdapter(new MyPagerAdapter());
        //设置ViewPager切换效果，即实现画廊效果
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //设置预加载数量
        mViewPager.setOffscreenPageLimit(2);
        //设置每页之间的左右间隔
        mViewPager.setPageMargin(100);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        //将容器的触摸事件反馈给ViewPager
        mViewPagerContainer.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return mViewPager.dispatchTouchEvent(event);
            }

        });
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //return viewList==null?0:viewList.size();
            return TOTAL_COUNT;//ViewPager里的个数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View item = LayoutInflater.from(activity).inflate(R.layout.item_gallery, null);
            ((ViewPager) container).addView(item);
            return item;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            //这里做切换ViewPager时，底部RadioButton的操作
            tvNum.setText((position + 1) + "/" + TOTAL_COUNT);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mViewPagerContainer != null) {
                mViewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    /**
     * 实现的原理是，在当前显示页面放大至原来的MAX_SCALE
     * 其他页面才是正常的的大小MIN_SCALE
     */
    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_SCALE = 1.1f;
        private static final float MIN_SCALE = 1.0f;//0.85f

        @Override
        public void transformPage(View view, float position) {
            //setScaleY只支持api11以上
            if (position < -1) {
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
//              Log.e("TAG", view + " , " + position + "");
                float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
                view.setScaleX(scaleFactor);
                //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
                if (position > 0) {
                    view.setTranslationX(-scaleFactor * 2);
                } else if (position < 0) {
                    view.setTranslationX(scaleFactor * 2);
                }
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]

                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);

            }
        }

    }


    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_gallery;
    }
}
