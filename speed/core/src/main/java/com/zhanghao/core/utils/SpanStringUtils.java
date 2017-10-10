package com.zhanghao.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者： zhanghao on 2017/10/9.
 * 功能：${des}
 */

public class SpanStringUtils {
    public static SpannableString replaceEmoji(Context context, String content) {
        final SpannableString spannableString = new SpannableString(content);
        String regexEmoji = "\\[([\u4e00-\u9fa5\\w\\:\\(\\)\\'\\>\\@\\-\\+\\*\\#\\{\\|\\}\\;\\^\\<(&lt;)$])+\\]";
        Pattern patternEmoji = Pattern.compile(regexEmoji);
        Matcher matcherEmoji = patternEmoji.matcher(spannableString);
        for (; ; ) { // 如果可以匹配到
            if (matcherEmoji.find()) {
                final String key = matcherEmoji.group(); // 获取匹配到的具体字符
                final int start = matcherEmoji.start(); // 匹配字符串的开始位置
                Integer imgRes = 0;  //通过获取的名字获取对饮本地的图片资源
                if (imgRes != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgRes, options);
                    try {
                        bitmap = smallTo1(bitmap);
                        ImageSpan span = new ImageSpan(context, bitmap);
                        spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception e) {
                    }
                }

            } else {
                break;
            }
        }
        Pattern active = Pattern.compile("#(.*?)#"); //匹配两个#的类型
        Matcher matcherAction = active.matcher(spannableString);
        while (matcherAction.find()) {
            final String key = matcherAction.group();
            final int start = matcherAction.start();
            spannableString.setSpan(getClickableSpanByStr(key), start, start + key.length(), 0);
        }
        return spannableString;
    }

    public static ClickableSpan getClickableSpanByStr(final String value) {
        char type = value.charAt(0);
        switch (type) {
            case '@':// @开头的@用户
                return new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.argb(255, 15, 129, 217));
                        ds.setUnderlineText(false);
                    }
                };
            case '#':// #开头的活动
                return new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.parseColor("#ff9100"));
                        ds.setUnderlineText(false);
                    }
                };
            case 'h':// http开头的超链接
                return new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.argb(255, 15, 129, 217));
                        ds.setUnderlineText(false);
                    }
                };
            default:
                return null;
        }
    }

    /**
     * Bitmap缩小的方法
     */
    public static Bitmap smallTo1(Bitmap bitmap) {
        if (bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        int targetHeight = DensityUtil.dp2px(18);
        float scale = (float) targetHeight / (float) bitmap.getHeight();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        if (bitmap == null) {
            return null;
        }
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
}


