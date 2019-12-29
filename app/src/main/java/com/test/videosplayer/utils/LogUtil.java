package com.test.videosplayer.utils;

import android.util.Log;


public class LogUtil {

    private static boolean open = true;

    public static void isOpen(boolean isOpen) {
        open = isOpen;
    }

    private static final String log_flag = "videoPlayer_log ";

    public static void d(String tag, String msg) {
        if (open) {
            if (tag.length() > 23) {
                tag = tag.substring(0, 23);
            }
            Log.d(tag, log_flag + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (open) {
            if (tag.length() > 23) {
                tag = tag.substring(0, 23);
            }
            Log.w(tag, log_flag + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (open) {
            if (tag.length() > 23) {
                tag = tag.substring(0, 23);
            }
            Log.e(tag, log_flag + msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (open) {
            if (tag.length() > 23) {
                tag = tag.substring(0, 23);
            }
            Log.e(tag, log_flag + msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (open) {
            if (tag.length() > 23) {
                tag = tag.substring(0, 23);
            }
            Log.i(tag, log_flag + msg);
        }
    }
}
