package com.test.videosplayer.video;

public class DurationUtil {

    /**
     * @param duration unit:ms
     * @return string
     */
    public static String getFormatedTime(int duration) {
        int totalSeconds = duration / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) :
                String.format("%02d:%02d", minutes, seconds);
    }
}
