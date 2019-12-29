package com.test.videosplayer.ui;

import android.graphics.SurfaceTexture;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.videosplayer.R;
import com.test.videosplayer.play.PlayThread;
import com.test.videosplayer.ui.adapter.LocalAdapter;
import com.test.videosplayer.utils.LogUtil;
import com.test.videosplayer.video.VideoBean;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "PlayerActivity";

    private TextView textView;
    private TextureView textureView;
    private VideoBean videoInfo;
    private Surface surface;
    private PlayThread playThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initViews();
        parseIntentExtra();
    }

    private void parseIntentExtra() {
        videoInfo = (VideoBean) getIntent().getExtras().getSerializable(LocalAdapter.EXTRA_VIDEO_INFO);
        if (null != videoInfo) {
            LogUtil.i(TAG, "recv videoInfo: " + videoInfo.toString());
        }

        MediaMetadataRetriever mediaMetadataRetriever;
    }

    private void initViews() {
        textView = findViewById(R.id.playAct_text_back);
        textureView = findViewById(R.id.playAct_textureView);

        textView.setOnClickListener(this);
        textureView.setSurfaceTextureListener(surfaceTextureListener);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playAct_text_back:
                stopPlayAndBack();
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
            surface = new Surface(surfaceTexture);
            playThread = new PlayThread(surface);
            playThread.start();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            surface = null;
            playThread.stopPlay();
            playThread.release();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };
}
