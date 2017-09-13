package com.leeone.classcard;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.leeone.classcard.mvp.MainContract;
import com.leeone.classcard.mvp.MainModel;
import com.leeone.classcard.mvp.MainPresenter;
import com.leeone.classcard.receiver.InnerRecevier;
import com.leeone.core.api.RetrofitClient;
import com.leeone.core.base.BaseActivity;
import com.leeone.core.utils.LogUtils;

import java.lang.reflect.Method;

import static android.R.attr.data;


public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {


    private BridgeWebView bridgeWebView;
    private Handler sHandler;
    private InnerRecevier innerReceiver;
    public static final String ACTION_REBOOT = "android.intent.action.REBOOT";
    public static final String ACTION_REQUEST_SHUTDOWN = "android.intent.action.ACTION_REQUEST_SHUTDOWN";
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowManagerParams;
    private View mEmptyView;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        myTitleBar.setTitle("班牌");
        myTitleBar.setVisibility(View.GONE);
        bridgeWebView = findView(R.id.bridgeWebView);
        bridgeWebView.setScrollContainer(false);
        bridgeWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        bridgeWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        // bridgeWebView.loadUrl(RetrofitClient.baseUrl + "/H5/Index/electronicTabletShowTemplate.html");
        //创建广播
        innerReceiver = new InnerRecevier();
        //动态注册广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //启动广播
        registerReceiver(innerReceiver, intentFilter);
//不使用缓存：

        bridgeWebView.setWebViewClient(new BridgeWebViewClient(bridgeWebView) {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.e(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                bridgeWebView.callHandler("getMac", getLocalMacAddressFromWifiInfo(MainActivity.this), new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        LogUtils.e("getMac成功");
                    }
                });
            }
        });

        bridgeWebView.callHandler("getMac", getLocalMacAddressFromWifiInfo(MainActivity.this), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                LogUtils.e("getMac成功");
            }
        });
        LogUtils.e(getLocalMacAddressFromWifiInfo(MainActivity.this));
    }

    @Override
    protected void initData() {
        mPresenter.checkRegister(getLocalMacAddressFromWifiInfo(activity));
        //forbiddenHomeKey();
    }

    //自动关机
    public void stopServer() {
//        Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
//        intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
//        //其中false换成true,会弹出是否关机的确认窗口
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

        PowerManager pManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        pManager.reboot(null);//重启</span>
        try {

            //获得ServiceManager类
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");

            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);

            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);

            //获得IPowerManager.Stub类
            Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //获得shutdown()方法
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class, boolean.class);
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager, false, true);

        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    //根据Wifi信息获取本地Mac
    public static String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    @Override
    protected int getContentView() {

        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideUi();
    }

    private void hideUi() {
        sHandler = new Handler();
        sHandler.post(mHideRunnable); // hide the navigation bar
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                sHandler.post(mHideRunnable); // hide the navigation bar
            }
        });
    }

    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            int flags;
            int curApiVersion = android.os.Build.VERSION.SDK_INT;
            if (curApiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
            } else {
                flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(innerReceiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.checkRegister(getLocalMacAddressFromWifiInfo(activity));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged : " + data);
        mPresenter.checkRegister(getLocalMacAddressFromWifiInfo(activity));
    }

    @Override
    public void checkRegister(boolean status) {
        if (status) {
            bridgeWebView.loadUrl(RetrofitClient.baseUrl + "/H5/Index/electronicTabletShowHome.html?devId=" + getLocalMacAddressFromWifiInfo(activity));
        } else {
            bridgeWebView.loadUrl(RetrofitClient.baseUrl + "/H5/Index/electronicTabletShowTemplate.html");
        }
    }

    private void forbiddenHomeKey(){
        mWindowManager = this.getWindowManager();
        mWindowManagerParams = new WindowManager.LayoutParams();
        mWindowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // internal system error windows, appear on top of everything they can
        mWindowManagerParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        // indicate this view don’t respond the touch event
        mWindowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        // add an empty view on the top of the window
        mEmptyView = new View(this);
        mWindowManager.addView(mEmptyView, mWindowManagerParams);
    }
}
