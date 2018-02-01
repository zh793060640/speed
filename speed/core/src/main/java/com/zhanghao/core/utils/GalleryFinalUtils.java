package com.zhanghao.core.utils;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;

/**
 * 作者： zhanghao on 2017/9/21.
 * 功能：${des}
 */

public class GalleryFinalUtils {
    private static final int REQUEST_CODE_CAMERA = 1002;
    private static final int REQUEST_CODE_GALLERY = 1001;

    public static void openGallerySingle(FunctionConfig functionConfig, GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback) {
        if (functionConfig == null) {
            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
        } else {
            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
        }
    }

    public static void openGalleryMuti(int max, GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback) {
        FunctionConfig config = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(max).setEnableCamera(true).setEnableEdit(true)
                .setEnableCrop(true)
                .build();
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, mOnHanlderResultCallback);

    }
}

