package com.tencent.qcloud.timchat.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.ui.TemplateTitle;

/**
 * 作者： zhanghao on 2018/3/2.
 * 功能：${des}
 */

public class BaiduMapActivity extends FragmentActivity {
    TemplateTitle title;
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private double latitude = 0;
    private BaiduSDKReceiver mBaiduReceiver;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    public MyLocationListenner myListener = new MyLocationListenner();
    BDLocation lastLocation = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidumap);

        mMapView = (MapView)findViewById(R.id.bmapView);
        title = (TemplateTitle)findViewById(R.id.map_title);
        title.setMoreTextContext("发送");
        title.setMoreTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocation();
            }
        });
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(10.0f);
        mBaiduMap.setMapStatus(msu);
        initMapView();
        mBaiduMap.setMyLocationEnabled(true);
        showMapWithLocationClient();
        final Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0);
        if (latitude != 0) {
            double longtitude = intent.getDoubleExtra("longitude", 0);
            String address = intent.getStringExtra("address");
            showMap(latitude, longtitude, address);
        }

        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mBaiduReceiver = new BaiduSDKReceiver();
        registerReceiver(mBaiduReceiver, iFilter);
    }

    public class BaiduSDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            String st1 = "网络出错";
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {

                String st2 = "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置";
                Toast.makeText(BaiduMapActivity.this, st2, Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(BaiduMapActivity.this, st1, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initMapView() {
        mMapView.setLongClickable(true);
    }
    LocationClient mLocClient;
    private void showMapWithLocationClient() {
        Toast.makeText(BaiduMapActivity.this, "正在确定你的位置", Toast.LENGTH_SHORT).show();

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// open gps
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setScanSpan(3000);
        mLocClient.setLocOption(option);
    }

    private void showMap(double latitude, double longtitude, String address) {
        title.setMoreTextContext("");
        LatLng ll = new LatLng(latitude, longtitude);
        MapStatus msu = new MapStatus.Builder().target(ll).zoom(18f).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(msu));

        MarkerOptions markop = new MarkerOptions();
        markop.position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.ease_icon_gcoding)).title(address);
        markop.animateType(MarkerOptions.MarkerAnimateType.drop);
        mBaiduMap.addOverlay(markop);
    }

    /**
     * format new location to string and show on screen
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || location.getAddress().address==null) {
                return;
            }

            if (lastLocation != null) {
                if (lastLocation.getLatitude() == location.getLatitude() && lastLocation.getLongitude() == location
                        .getLongitude()) {

                    // mMapView.refresh(); //need this refresh?
                    return;
                }
            }
            lastLocation = location;
// 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
// 设置定位数据
            if (latitude == 0) {
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus msu = new MapStatus.Builder().target(ll).zoom(18f).build();
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(msu));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        if (mLocClient != null) {
            mLocClient.stop();
        }
        super.onPause();
        lastLocation = null;
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        if (mLocClient != null) {
            mLocClient.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mLocClient != null)
            mLocClient.stop();
        mMapView.onDestroy();
        unregisterReceiver(mBaiduReceiver);
        super.onDestroy();
    }

    public void sendLocation() {
        if (lastLocation==null) {
            return;
        }
        Intent intent = this.getIntent();
        intent.putExtra("latitude", lastLocation.getLatitude());
        intent.putExtra("longitude", lastLocation.getLongitude());
        intent.putExtra("address", lastLocation.getAddrStr());
        this.setResult(RESULT_OK, intent);
        finish();

    }

    public static void toBaiduMapActivity(Context context,String des,double latitude, double longitude){
        Intent intent = new Intent(context, BaiduMapActivity.class);
        intent.putExtra("address", des);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        context.startActivity(intent);
    }
}
