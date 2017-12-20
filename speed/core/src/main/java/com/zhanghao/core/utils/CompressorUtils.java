package com.zhanghao.core.utils;


import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * 作者： zhanghao on 2017/9/27.
 * 功能：${des} 压缩图片工具类
 */

public class CompressorUtils {
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


    public interface CompressorListener {
        void result(File file);
    }


}
