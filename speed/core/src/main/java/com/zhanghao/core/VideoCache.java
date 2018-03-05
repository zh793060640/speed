package com.zhanghao.core;

import com.danikula.videocache.HttpProxyCacheServer;
import com.zhanghao.core.utils.AppManager;
import com.zhanghao.core.utils.FileUtils;

/**
 * 作者： zhanghao on 2018/3/5.
 * 功能：${des}
 */

public class VideoCache {

    private static HttpProxyCacheServer proxy;

    public static void init() {
        proxy = new HttpProxyCacheServer.Builder(AppManager.I().getApplicationContext())
                .cacheDirectory(FileUtils.getCachePath(AppManager.I().getApplicationContext(), "video"))
                .build();
    }

    public static HttpProxyCacheServer getProxy() {

        return proxy == null ? (proxy = newProxy()) : proxy;
    }

    private static HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(AppManager.I().getApplicationContext())
                .cacheDirectory(FileUtils.getCachePath(AppManager.I().getApplicationContext(), "video"))
                .build();
    }
}
