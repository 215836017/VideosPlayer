package com.test.videosplayer;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

import com.test.videosplayer.utils.LogUtil;

public class MediaCoverHelper {

    private final String TAG = "MediaCoverHelper";

    private final String strStorage = "/storage/emulated/0/";
    private final String strSdcard = "/sdcard/";

    private final int MAX_SCAN_TIME = 10 * 1000;

    private MediaMetadataRetriever mediaMetadataRetriever;


    public MediaCoverHelper() {
        mediaMetadataRetriever = new MediaMetadataRetriever();
    }

    private String getRealPath(String path) {

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        if (path.indexOf(strStorage) == 0 && path.contains(strStorage)) {
            return path.replace(strStorage, strSdcard);
        }

        return path;

    }

    public Bitmap getVideoCover(String filePath) {
        // TODO  获取视频封面失败........ fuck
        LogUtil.i(TAG, "getFirstFrame() -- filePath = " + filePath);
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        String realPath = getRealPath(filePath);
        if (TextUtils.isEmpty(realPath)) {
            return null;
        } else {
            LogUtil.i(TAG, "getFirstFrame() -- realPath = " + realPath);
        }

        Bitmap frameAtTime;
        try {
            mediaMetadataRetriever.setDataSource(realPath);
        } catch (Exception e) {
            return null;
        }
//        String videoDur = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//        LogUtil.i(TAG, "getFirstFrame() -- videoDur = " + videoDur);
        frameAtTime = mediaMetadataRetriever.getFrameAtTime(0);
        for (int time = 0; time < MAX_SCAN_TIME; time += 100) {
            frameAtTime = mediaMetadataRetriever.getFrameAtTime(0);
            if (null != frameAtTime) {
                break;
            }
        }

        return frameAtTime;
    }

    public void release() {
        if (null != mediaMetadataRetriever) {
            mediaMetadataRetriever.release();
            mediaMetadataRetriever = null;
        }
    }
}
