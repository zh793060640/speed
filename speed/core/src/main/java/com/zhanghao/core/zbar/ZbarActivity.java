package com.zhanghao.core.zbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.zhanghao.core.R;
import com.zhanghao.core.base.BaseActivity;
import com.zhanghao.core.base.BaseModle;
import com.zhanghao.core.base.BasePresenter;


/**
 * 二维码扫描的界面
 */
public class ZbarActivity extends BaseActivity<BasePresenter, BaseModle> implements QRCodeView.Delegate {
    private static final String TAG = ZbarActivity.class.getSimpleName();
    private QRCodeView mQRCodeView;

    @Override
    protected void onRestart() {
        mQRCodeView.startSpotAndShowRect();
        mQRCodeView.startCamera();
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getContentView() {
        return R.layout.zbar_activity;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        myTitleBar.setTitle("二维码扫描");
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpotAndShowRect();
    }

    @Override
    protected void initData() {

    }

    /**
     * 调用手机振动的方法
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        //vibrate();
        Intent resultIntent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("result", result);
        resultIntent.putExtras(bundle);
        this.setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


}
