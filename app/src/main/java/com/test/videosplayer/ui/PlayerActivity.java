package com.test.videosplayer.ui;

import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.videosplayer.R;
import com.test.videosplayer.play.MediaPlayerManager;
import com.test.videosplayer.ui.adapter.LocalAdapter;
import com.test.videosplayer.utils.LogUtil;
import com.test.videosplayer.video.DurationUtil;
import com.test.videosplayer.video.VideoBean;

public class PlayerActivity extends AppCompatActivity {

    private final String TAG = "PlayerActivity";

    private TextureView textureView;
    private RelativeLayout layoutControl;
    private ImageView imageBackward, imagePlay, iamgeForward;
    private SeekBar seekBar;
    private TextView textCurrentDuration, textTotalDuration;

    private VideoBean videoInfo;
    private Surface surface;
    private MediaPlayerManager mediaPlayerManager;
    private boolean isPlaying = false;
    private boolean isSlided = false;

    private final int TIME_TO_HIDE_CONTROL_VIEWS = 3000;

    private final int MSG_PLAY_STARTED = 0x10;
    private final int MSG_TIME_COUNT = 0x11;
    private final int MSG_HIDE_CONTROL_VIEWS = 0x12;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_PLAY_STARTED:
                    playing();
                    break;

                case MSG_TIME_COUNT:
                    updatePlayProgress();
                    break;

                case MSG_HIDE_CONTROL_VIEWS:
                    layoutControl.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_player);

        initViews();
        parseIntentExtra();
        mediaPlayerManager = new MediaPlayerManager(onMediaPlayerListner);
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayerManager.pausePlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void release() {

    }

    private void initViews() {
        textureView = findViewById(R.id.play_activity_tv);
        layoutControl = findViewById(R.id.play_activity_rl_control);
        imageBackward = findViewById(R.id.play_activity_iv_rew);
        imagePlay = findViewById(R.id.play_activity_iv_play);
        iamgeForward = findViewById(R.id.play_activity_iv_ff);
        seekBar = findViewById(R.id.play_activity_sb);
        textCurrentDuration = findViewById(R.id.play_activity_tv_current_duration);
        textTotalDuration = findViewById(R.id.play_activity_tv_total_duration);
    }

    private void parseIntentExtra() {
        videoInfo = (VideoBean) getIntent().getExtras().getSerializable(LocalAdapter.EXTRA_VIDEO_INFO);
        if (null != videoInfo) {
            LogUtil.i(TAG, "recv videoInfo: " + videoInfo.toString());
            seekBar.setMax(videoInfo.getDuration());
            textTotalDuration.setText(DurationUtil.getFormatedTime(videoInfo.getDuration()));

        } else {
            Toast.makeText(this, "获取视频信息失败！", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setListeners() {
        textureView.setSurfaceTextureListener(surfaceTextureListener);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    private void playing() {
        isPlaying = true;
        handler.sendEmptyMessage(MSG_TIME_COUNT);
        showControlViews();
    }

    private void updatePlayProgress() {
        int currentPosition = mediaPlayerManager.getCurrentPosition();
        seekBar.setProgress(currentPosition);
        textCurrentDuration.setText(DurationUtil.getFormatedTime(currentPosition));
        handler.sendEmptyMessageDelayed(MSG_TIME_COUNT, 1000);
    }

    private void showControlViews() {
        if (layoutControl.getVisibility() == View.VISIBLE) {
            layoutControl.setVisibility(View.INVISIBLE);
            hideNavigationBar();

        } else {
            showNavigationBar();
            layoutControl.setVisibility(View.VISIBLE);
            handler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL_VIEWS, TIME_TO_HIDE_CONTROL_VIEWS);
        }
    }

    public void playActivityViewsClick(View v) {
        switch (v.getId()) {
            case R.id.play_activity_iv_back:
                finish();
                break;

            case R.id.play_activity_iv_rew:
                moveToDesignatedProgress(false);
                break;

            case R.id.play_activity_iv_play:
                playOrPause();
                break;

            case R.id.play_activity_iv_ff:
                moveToDesignatedProgress(true);
                break;
        }
    }

    private void moveToDesignatedProgress(boolean isForward) {
        showToast("wait to finish...");
    }

    private void playOrPause() {
        if (mediaPlayerManager.isPlaying()) {
            mediaPlayerManager.pausePlay();
            imagePlay.setImageResource(R.drawable.ic_media_play);

        } else {
            mediaPlayerManager.resumePlay();
            imagePlay.setImageResource(R.drawable.ic_media_pause);
        }
    }

    private int downX;
    private int downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.i(TAG, "onTouchEvent() -- ACTION_DOWN");
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                LogUtil.i(TAG, "onTouchEvent() -- ACTION_MOVE");
//                isSlided = true;
                onMoveInScreen((int) event.getX(), (int) event.getY());
                break;

            case MotionEvent.ACTION_UP:
                LogUtil.i(TAG, "onTouchEvent() -- ACTION_UP");
//                if (!isSlided) {
//                    showControlViews();
//                }
                showControlViews();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private final int LIMIT_X = 30;
    private final int LIMIT_Y = 20;

    private void onMoveInScreen(int posX, int posY) {
        if (Math.abs(posX - downX) > Math.abs(posY - downY)) {
            // move in horizontal

        } else {
            // move in vertical
        }
    }

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            LogUtil.i(TAG, "onSurfaceTextureAvailable()  ---- 11111");
            surface = new Surface(surfaceTexture);
            mediaPlayerManager.startPlay(videoInfo.getData(), surface);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            surface = null;
            mediaPlayerManager.release();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private MediaPlayerManager.OnMediaPlayerListner onMediaPlayerListner = new MediaPlayerManager.OnMediaPlayerListner() {
        @Override
        public void onError(int errorCode) {
        }

        @Override
        public void onPlayStarted() {
            handler.sendEmptyMessage(MSG_PLAY_STARTED);
        }
    };

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
