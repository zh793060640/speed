package com.zhanghao.core;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.zhanghao.core.base.BaseNormalActivity;
import com.zhanghao.core.ui.IVideoView;

import java.io.File;

/**
 * 作者： zhanghao on 2018/3/5.
 * 功能：${des}
 */

public class ShowVideoActivity extends BaseNormalActivity implements CacheListener {
    private static final String LOG_TAG = "ShowVideoActivity";
    String url;
    IVideoView videoView;
    SeekBar progressBar;
    private final VideoProgressUpdater updater = new VideoProgressUpdater();
    private boolean dialgoShow = true;
    private int currenPostion;

    @Override
    protected void initView() {

        myTitleBar.setVisibility(View.GONE);
        videoView = (IVideoView) findViewById(R.id.videoView);
        progressBar = (SeekBar) findViewById(R.id.progressBar);
        url = getIntent().getStringExtra("url");
        checkCachedState();
        startVideo();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startVideo();
            }
        });
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int videoPosition = videoView.getDuration() * progressBar.getProgress() / 100;
                videoView.seekTo(videoPosition);
            }
        });
    }

    private void checkCachedState() {
        HttpProxyCacheServer proxy = VideoCache.getProxy();
        boolean fullyCached = proxy.isCached(url);
        if (fullyCached) {
            progressBar.setSecondaryProgress(100);
            dialgoShow = false;
        }else {
            showDialog("正在准备播放...");
        }
    }
    private void startVideo() {

        HttpProxyCacheServer proxy = VideoCache.getProxy();
        proxy.registerCacheListener(this, url);
        String proxyUrl = proxy.getProxyUrl(url);
        Log.d(LOG_TAG, "Use proxy url " + proxyUrl + " instead of original url " + url);
        videoView.setVideoPath(proxyUrl);
        videoView.start();
    }
    @Override
    protected void initData() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_show_video;
    }

    @Override
    public void onResume() {
        super.onResume();
        updater.start();
        videoView.start();
        videoView.seekTo(currenPostion);
    }

    @Override
    public void onPause() {
        super.onPause();
        videoView.pause();
        updater.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        videoView.stopPlayback();
        VideoCache.getProxy().unregisterCacheListener(this);

    }

    private void updateVideoProgress() {
        int videoProgress = videoView.getCurrentPosition() * 100 / videoView.getDuration();
        if(videoProgress>0&&dialgoShow){
            dissmissDialog();
            dialgoShow = false;
        }
        currenPostion = videoView.getCurrentPosition();
        progressBar.setProgress(videoProgress);
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        progressBar.setSecondaryProgress(percentsAvailable);
        Log.d(LOG_TAG, String.format("onCacheAvailable. percents: %d, file: %s, url: %s", percentsAvailable, cacheFile, url));
    }

    private final class VideoProgressUpdater extends Handler {

        public void start() {
            sendEmptyMessage(0);
        }

        public void stop() {
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message msg) {
            updateVideoProgress();
            sendEmptyMessageDelayed(0, 100);
        }
    }

    /**
     * 监听屏幕方向改变
     * @param newConfig
     * 配置文件配置了configChanges才会走次此回调
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //配置文件中设置 android:configChanges="orientation|screenSize|keyboardHidden" 不然横竖屏切换的时候重新创建又重新播放
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            videoView.setLayoutParams(params);

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);//显示顶部状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(250));
            videoView.setLayoutParams(params);

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏不 显示顶部状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
        System.out.println("======onConfigurationChanged===");
    }
}
