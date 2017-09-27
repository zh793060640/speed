package com.zhanghao.core.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

/**
 * 作者： zhanghao on 2017/9/27.
 * 功能：${des} 压缩图片工具类
 */

public class CompressorUtils {
    public static File CompressorImage(String filePath, String actualImage) {
        File result = null;
        try {
            if (actualImage != null) {
                result = new Compressor(AppManager.I().currentActivity())
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(75)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .setDestinationDirectoryPath(actualImage)
                        .compressToFile(new File(filePath));
            }
            result = new Compressor(AppManager.I().currentActivity())
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .compressToFile(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
