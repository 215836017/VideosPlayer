package com.test.videosplayer.video;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.test.videosplayer.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

    public boolean createVideoCover(String filePath, String videoName) {
        // TODO  获取视频封面失败........ fuck
        // 1. 用MediaStore的方式查询的路径需要转换
        // 2. 视频解析失败(非视频)时 MediaMetadataRetriever.setDataSource会走到异常。
        LogUtil.i(TAG, "getFirstFrame() -- filePath = " + filePath);
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        String realPath = getRealPath(filePath);
        if (TextUtils.isEmpty(realPath)) {
            return false;
        } else {
            LogUtil.i(TAG, "getFirstFrame() -- realPath = " + realPath);
        }

        Bitmap frameAtTime;
        try {
            mediaMetadataRetriever.setDataSource(realPath);
        } catch (Exception e) {
            LogUtil.i(TAG, "getFirstFrame() --  mediaMetadataRetriever.setDataSource failed");
            return false;
        }
//        String videoDur = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//        LogUtil.i(TAG, "getFirstFrame() -- videoDur = " + videoDur);

        for (int time = 0; time < MAX_SCAN_TIME; time += 100) {
            frameAtTime = mediaMetadataRetriever.getFrameAtTime(0);
            if (null != frameAtTime) {
                createCoverOfVideo(videoName, frameAtTime);
                break;
            }
        }
        return true;
    }

    private void createCoverOfVideo(String fileName, Bitmap cover) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/videosPlayer/cover/" + fileName + ".png";
        File file = new File(fullPath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        OutputStream outputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);

            if (cover.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            LogUtil.i(TAG, "createCoverOfVideo() failed....");

        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void release() {
        if (null != mediaMetadataRetriever) {
            mediaMetadataRetriever.release();
            mediaMetadataRetriever = null;
        }
    }

    public static Bitmap getVideoCover(String videoName) {
        if (TextUtils.isEmpty(videoName)) {
            return null;
        }

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/videosPlayer/cover/" + videoName + ".png";
//// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(fullPath, options);
//        // 调用上面定义的方法计算inSampleSize值
//        options.inSampleSize = calculateInSampleSize(options, 100, 100);
//        // 使用获取到的inSampleSize值再次解析图片
//        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(fullPath);

//        return ThumbnailUtils.createVideoThumbnail(fullPath, MediaStore.Images.Thumbnails.MINI_KIND);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
