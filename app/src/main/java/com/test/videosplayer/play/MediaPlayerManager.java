package com.test.videosplayer.play;

import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.media.TimedMetaData;
import android.media.TimedText;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.NonNull;

import com.test.videosplayer.utils.LogUtil;

import java.io.IOException;

public class MediaPlayerManager {

    private final String TAG = "MediaPlayerManager";

    public interface OnMediaPlayerListner {
        void onError(int errorCode);
    }

    private OnMediaPlayerListner listner;

    private MediaPlayer mediaPlayer;

    public MediaPlayerManager(OnMediaPlayerListner onMediaPlayerListner) {
        init();
        listner = onMediaPlayerListner;
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(onErrorListener);
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        mediaPlayer.setOnPreparedListener(onPreparedListener);
        mediaPlayer.setOnSeekCompleteListener(onSeekCompleteListener);
        mediaPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
        mediaPlayer.setOnTimedTextListener(onTimedTextListener);
    }

    public void startPlay(String path, Surface surface) {
        mediaPlayer.setSurface(surface);

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resumePlay() {
        mediaPlayer.start();
    }

    public void pausePlay() {
        mediaPlayer.pause();
    }

    public void stopPlay() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void release() {
        stopPlay();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            LogUtil.d(TAG, "onPrepared() -- 11111");

            mediaPlayer.start();
        }
    };

    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            LogUtil.d(TAG, "onError() what = " + what + ", extra = " + extra);
            return false;
        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            LogUtil.d(TAG, "onCompletion() -- 11111");
        }
    };

    private MediaPlayer.OnSeekCompleteListener onSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(MediaPlayer mp) {
            LogUtil.d(TAG, "onSeekComplete() -- 11111");

        }
    };

    private MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            LogUtil.d(TAG, "onVideoSizeChanged() -- width = " + width + ", height = " + height);
        }
    };

    private MediaPlayer.OnTimedTextListener onTimedTextListener = new MediaPlayer.OnTimedTextListener() {
        @Override
        public void onTimedText(MediaPlayer mp, TimedText text) {
            LogUtil.d(TAG, "onTimedText() -- 11111");
        }
    };
}
