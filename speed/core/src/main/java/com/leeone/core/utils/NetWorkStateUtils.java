package com.leeone.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 这是一个工具类，用于判断当前网络状态
 * 要求：判断当前所处的网络装填：wifi,手机网络,无网络
 *
 * 1.Monitor network connections (Wi-Fi, GPRS, UMTS, etc.)
 *          监控网络连接，可以获取到网络的连接的具体信息。网络连接状态，速度等。
 * 2.Send broadcast intents when network connectivity changes
 *          当网络连接改变的时候发送广播。
 * 3.Attempt to "fail over" to another network when connectivity to a network is lost
 *          当一个网络连接失败的时候，会自动连入下一个网络
 *          比如：当流量开关打开的时候，而wifi连接上，没问题，一旦wifi断开，将会自动连接上手机网络
 * 4.Provide an API that allows applications to query the coarse-grained or fine-grained state of the available networks
 *          提供了API，可以用来查询当前可用的网络连接
 *  5. Provide an API that allows application to request and select networks for their data traffic
 *  `       提供了API，可以用来自己选择网络
 */
public class NetWorkStateUtils {
    /**
     * @return 是否是wifi
     */
    public static boolean isWifi(Context context){
       return isWifiOrPhone(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * @return 是否是手机网络
     */
    public static boolean isPhone(Context context){
        return isWifiOrPhone(context, ConnectivityManager.TYPE_MOBILE);
    }

    private static boolean isWifiOrPhone(Context context, int typeMobile) {
        if (!isConnected(context)){
            //如果没有连接上任何网络，那么直接返回false，表示连接失败
            return false;
        }
        //获取到网络连接管理器
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取到mobile状态下的网络
        NetworkInfo networkInfo = manager.getNetworkInfo(typeMobile);
        if (networkInfo==null){
            //如果为null（可能是平板或者网络极差），直接返回false
            return false;
        }
        //如果两个条件都满足，则返回true;
        return networkInfo.isAvailable() & networkInfo.isConnected();
    }

    /**
     * @return 是否连接上
     */
    public static boolean isConnected(Context context){
        //通过系统的服务，获取到连接的管理器
        ConnectivityManager manager= (ConnectivityManager) AppManager.I().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//                manager.getActiveNetwork();//这个方法，是在SDK23才添加的，所以，不建议使用
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();//这个方法，实在SDK1就有了，所以没问题
        //如果没有，那么直接返回false，表示没有可用的网络连接
        if (activeNetworkInfo== null){
            return false;
        }
        //判断，当两个都满足的时候才返回true，其他的都返回false；
        return activeNetworkInfo.isConnected() & activeNetworkInfo.isAvailable();

    }


}
