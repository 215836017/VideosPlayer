package com.test.videosplayer.ui;

import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.videosplayer.R;
import com.test.videosplayer.play.MediaPlayerManager;
import com.test.videosplayer.ui.adapter.LocalAdapter;
import com.test.videosplayer.utils.LogUtil;
import com.test.videosplayer.video.VideoBean;

public class PlayerActivity extends AppCompatActivity {

    private final String TAG = "PlayerActivity";

    private TextView textView;
    private TextureView textureView;
    private Button btnPlay;
    private VideoBean videoInfo;
    private Surface surface;
    private MediaPlayerManager mediaPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_player);

        initViews();
        parseIntentExtra();
        mediaPlayerManager = new MediaPlayerManager(onMediaPlayerListner);
    }

    private void parseIntentExtra() {
        videoInfo = (VideoBean) getIntent().getExtras().getSerializable(LocalAdapter.EXTRA_VIDEO_INFO);
        if (null != videoInfo) {
            LogUtil.i(TAG, "recv videoInfo: " + videoInfo.toString());
        } else {
            Toast.makeText(this, "获取视频信息失败！", Toast.LENGTH_LONG).show();
            stopPlayAndBack();
        }
    }

    private void initViews() {
        textView = findViewById(R.id.play_activity_text_back);
        textureView = findViewById(R.id.play_activity_tv);
        btnPlay = findViewById(R.id.play_activity_btn_play);

        textureView.setSurfaceTextureListener(surfaceTextureListener);
    }


    public void playActivityViewsClick(View v) {
        switch (v.getId()) {
            case R.id.play_activity_text_back:
                stopPlayAndBack();
                break;

            case R.id.play_activity_btn_play:
                if (null != mediaPlayerManager) {
                    mediaPlayerManager.startPlay(videoInfo.getData(), surface);
                    btnPlay.setVisibility(View.INVISIBLE);
                }
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            stopPlayAndBack();
        }
        return true;
    }

    private void stopPlayAndBack() {
        finish();
    }

    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            LogUtil.i(TAG, "onSurfaceTextureAvailable()  ---- 11111");
            surface = new Surface(surfaceTexture);
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

    private MediaPlayerManager.OnMediaPlayerListner onMediaPlayerListner = new MediaPlayerManager.OnMediaPlayerListner() {
        @Override
        public void onError(int errorCode) {
            stopPlayAndBack();
        }
    };
}
