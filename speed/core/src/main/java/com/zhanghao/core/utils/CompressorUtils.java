package com.zhanghao.core.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * 作者： zhanghao on 2017/9/27.
 * 功能：${des} 压缩图片工具类
 */

public class CompressorUtils {
    //单张
    public static void CompressorImage(final String filePath, final CompressorListener listener) {
        Luban.with(AppManager.I().getApplicationContext())
                .load(filePath)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                //   .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (listener != null) {
                            listener.result(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.result(new File(filePath));
                        }
                    }
                }).launch();    //启动压缩
    }

    //多张
    public static void CompressorImages(final List<String> images, final CompressorImagesListener listener) {
        if (EmptyUtils.isEmpty(images)) {
            return;
        }

        final List<Boolean> status = new ArrayList<>();
        final String[] result = new String[images.size()];
        for (int i = 0; i < images.size(); i++) {
            final int finalI = i;
            Luban.with(AppManager.I().getApplicationContext())
                    .load(images.get(i))                                   // 传人要压缩的图片列表
                    .ignoreBy(100)                                  // 忽略不压缩图片的大小
                    //   .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            status.add(true);
                            result[finalI] = file.getAbsolutePath();
                            if (status.size() == images.size() && listener != null) {
                                listener.result(Arrays.asList(result));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            status.add(false);
                            result[finalI] = images.get(finalI);
                            if (status.size() == images.size() && listener != null) {
                                listener.result(Arrays.asList(result));
                            }

                        }
                    }).launch();    //启动压缩
        }

    }

    public interface CompressorListener {
        void result(File file);
    }

    public interface CompressorImagesListener {
        void result(List<String> data);
    }

}
