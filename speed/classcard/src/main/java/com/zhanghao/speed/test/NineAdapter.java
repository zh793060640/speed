package com.zhanghao.speed.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.zhanghao.core.ui.MultiImageView;
import com.zhanghao.speed.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： zhanghao on 2017/10/9.
 * 功能：${des}
 */

public class NineAdapter extends CommonAdapter<ThumbViewInfo> {
    public List<String> data = new ArrayList<>();
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();

    public NineAdapter(Context context, int layoutId, List<ThumbViewInfo> datas) {
        super(context, layoutId, datas);
        List<String> urls = ImageUrlConfig.getUrls();
        for (int i = 0; i < 9; i++) {
            data.add(urls.get(i));
            mThumbViewInfoList.add(new ThumbViewInfo(urls.get(i)));
        }
    }

    @Override
    protected void convert(ViewHolder holder, ThumbViewInfo thumbViewInfo, int position) {
        final MultiImageView multiImageView = (MultiImageView) holder.getView(R.id.multiImageView);
        multiImageView.setList(data);
        multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                computeBoundsBackward(multiImageView, data);//组成数据
                GPreviewBuilder.from((Activity) mContext)
                        .setData(mThumbViewInfoList)
                        .setCurrentIndex(position)
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();//启动
            }
        });
    }

    /**
     * 查找信息
     *
     * @param list 图片集合
     */
    private void computeBoundsBackward(MultiImageView view, List<String> list) {
        ThumbViewInfo item;
        mThumbViewInfoList.clear();
        for (int i = 0; i < view.getChildCount(); i++) {
            View itemView = view.getChildAt(i);
            if (itemView instanceof LinearLayout) {
                int count = ((LinearLayout) itemView).getChildCount();
                for (int i1 = 0; i1 < count; i1++) {
                    Rect bounds = new Rect();
                    ImageView image = (ImageView) ((LinearLayout) itemView).getChildAt(i1);
                    item = new ThumbViewInfo(list.get(3*i+i1));
                    image.getGlobalVisibleRect(bounds);
                    item.setBounds(bounds);
                    mThumbViewInfoList.add(item);
                }
            }
        }
    }
}

