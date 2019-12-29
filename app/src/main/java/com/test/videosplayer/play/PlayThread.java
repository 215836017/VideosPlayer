package com.test.videosplayer.play;

import android.view.Surface;

public class PlayThread extends Thread {

    private Surface surface;

    public PlayThread(Surface surface) {
        this.surface = surface;
    }

    @Override
    public void run() {
        super.run();
    }

    public void stopPlay() {

    }

    public void release() {

    }

}
