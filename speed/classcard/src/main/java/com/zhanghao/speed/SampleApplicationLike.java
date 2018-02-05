package com.zhanghao.speed;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.previewlibrary.ZoomMediaLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.utils.Foreground;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhanghao.core.api.RetrofitClient;
import com.zhanghao.core.utils.AppManager;
import com.zhanghao.core.utils.GlideImageLoader;
import com.zhanghao.core.utils.Utils;
import com.zhanghao.speed.test.TestImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;

/**
 * 作者： zhanghao on 2017/7/26.
 * 功能：${des}
 */

public class SampleApplicationLike extends DefaultApplicationLike {


    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplication(), "2fd77717a9", true);//初始化bugly
        AppManager.I().init(getApplication());//初始化activity管理类
        Utils.init(getApplication());//初始化一个通用的utils
        RetrofitClient.init(BuildConfig.SERVER_ADDRESS);//初始化网络请求库

        Stetho.initialize(
                Stetho.newInitializerBuilder(getApplication())
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplication()))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplication()))
                        .build());
        initPhotoSelete();//初始化图片选择器
        initShare();//初始化第三方分享
        ZoomMediaLoader.getInstance().init(new TestImageLoader());//9图查看

        JPushInterface.setDebugMode(true);
        JPushInterface.init(AppManager.I().getApplicationContext());

        Foreground.init(getApplication());
        MyApplication.context = getApplication().getApplicationContext();
        if (MsfSdkUtils.isMainProcess(getApplication())) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                        //消息被设置为需要提醒
                        notification.doNotify(MyApplication.context, com.tencent.qcloud.timchat.R.mipmap.ic_launcher);
                    }
                }
            });
        }
    }

    public void initShare() {
        UMShareAPI.get(getApplication());
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    public void initPhotoSelete() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.parseColor("#ff9100")).setCheckNornalColor(R.color.green).setCheckSelectedColor(Color.parseColor("#ff9100")).setCropControlColor(R.color.green)
        .setIconCamera(R.drawable.icon_record_camera).setFabNornalColor(Color.parseColor("#ff9100")).setFabPressedColor(Color.parseColor("#ff9100"))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setForceCrop(true)
                .setEnablePreview(false)
                .build();
        //配置imageloader
        ImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(getApplication().getApplicationContext(), imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }


}
