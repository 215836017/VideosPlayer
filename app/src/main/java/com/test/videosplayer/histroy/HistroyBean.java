package com.test.videosplayer.histroy;

import com.test.videosplayer.video.VideoBean;

public class HistroyBean {

    private VideoBean videoBean;
    private int playedDuration;
    private int playedPercent;

    public HistroyBean(VideoBean videoBean, int playedDuration, int playedPercent) {
        this.videoBean = videoBean;
        this.playedDuration = playedDuration;
        this.playedPercent = playedPercent;
    }
}
