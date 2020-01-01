package com.test.videosplayer.video;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.test.videosplayer.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VideoFinder extends Thread {

    private final String TAG = "VideoFinder";

    public interface OnQueryVideosListener {
        void onQueryVideosFinish(List<VideoBean> videosList);
    }

    private Context context;
    private OnQueryVideosListener onQueryVideosListener;
    private MediaCoverHelper mediaCoverHelper;

    public VideoFinder(Context context, OnQueryVideosListener onQueryVideosListener) {
        this.context = context;
        this.onQueryVideosListener = onQueryVideosListener;
        mediaCoverHelper = new MediaCoverHelper();
    }

    @Override
    public void run() {
        super.run();

        queryAllVideos();
    }

    private void queryAllVideos() {

        List<VideoBean> videosList = new ArrayList<>();

        Cursor cursor = context.getApplicationContext().getContentResolver().
                query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, null);

        if (null != cursor && cursor.getCount() > 0) {
            LogUtil.i(TAG, "start to list...");
            int testCount = 0;

            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                if (data.endsWith(".mp3")) {
                    continue;
                }
                String default_sort_order = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DEFAULT_SORT_ORDER));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                int bookMark = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BOOKMARK));
                String bucket_display_name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                String bucket_id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.CATEGORY));
                int date_taken = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DESCRIPTION));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                int is_private = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.IS_PRIVATE));
                String language = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.LANGUAGE));
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.LONGITUDE));
                int mini_thumb_magic = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MINI_THUMB_MAGIC));
                String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                String tags = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TAGS));

                int date_added = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED));
                int date_modified = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));
                String display_name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                int height = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT));
                String mime_type = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                int width = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));

                if (0 == testCount) {
                    LogUtil.d(TAG, "default_sort_order = " + default_sort_order);
                    LogUtil.d(TAG, "album = " + album);
                    LogUtil.d(TAG, "artist = " + artist);
                    LogUtil.d(TAG, "bookMark = " + bookMark);
                    LogUtil.d(TAG, "bucket_display_name = " + bucket_display_name);
                    LogUtil.d(TAG, "bucket_id = " + bucket_id);
                    LogUtil.d(TAG, "category = " + category);
                    LogUtil.d(TAG, "date_taken = " + date_taken);
                    LogUtil.d(TAG, "description = " + description);
                    LogUtil.d(TAG, "duration = " + duration);
                    LogUtil.d(TAG, "is_private = " + is_private);
                    LogUtil.d(TAG, "language = " + language);
                    LogUtil.d(TAG, "latitude = " + latitude);
                    LogUtil.d(TAG, "longitude = " + longitude);
                    LogUtil.d(TAG, "mini_thumb_magic = " + mini_thumb_magic);
                    LogUtil.d(TAG, "resolution = " + resolution);
                    LogUtil.d(TAG, "tags = " + tags);
                    LogUtil.d(TAG, "data = " + data);
                    LogUtil.d(TAG, "date_added = " + date_added);
                    LogUtil.d(TAG, "date_modified = " + date_modified);
                    LogUtil.d(TAG, "display_name = " + display_name);
                    LogUtil.d(TAG, "height = " + height);
                    LogUtil.d(TAG, "width = " + width);
                    LogUtil.d(TAG, "mime_type = " + mime_type);
                    LogUtil.d(TAG, "size = " + size);
                    LogUtil.d(TAG, "id = " + id);
                    LogUtil.d(TAG, "title = " + title);
                }

//                if (mediaCoverHelper.createVideoCover(data, title)) {
                videosList.add(new VideoBean(id, default_sort_order, album, artist, bookMark,
                        bucket_display_name, bucket_id, category, date_taken,
                        description, duration, is_private, language,
                        latitude, longitude, mini_thumb_magic, resolution,
                        tags, data, date_added, date_modified,
                        display_name, height, mime_type, size, title,
                        width));

//                }
            }

//            Collections.sort(videosList, new Comparator<VideoBean>() {
//                @Override
//                public int compare(VideoBean o1, VideoBean o2) {
//                    return o1.getData().compareTo(o2.getData());
//                }
//            });

        } else {
            LogUtil.e(TAG, "return null");
        }

        if (null != onQueryVideosListener) {
            onQueryVideosListener.onQueryVideosFinish(videosList);
        } else {
            throw new NullPointerException("onQueryVideosListener is null");
        }
    }
    /*
com.test.videosplayer D/VideoFinder.java: videoPlay_log default_sort_order = wx_camera_1484820710584
com.test.videosplayer D/VideoFinder.java: videoPlay_log album = <unknown>
com.test.videosplayer D/VideoFinder.java: videoPlay_log artist = <unknown>
com.test.videosplayer D/VideoFinder.java: videoPlay_log bookMark = null
com.test.videosplayer D/VideoFinder.java: videoPlay_log bucket_display_name = WeiXin
com.test.videosplayer D/VideoFinder.java: videoPlay_log bucket_id = -924335728
com.test.videosplayer D/VideoFinder.java: videoPlay_log category = null
com.test.videosplayer D/VideoFinder.java: videoPlay_log date_taken = 1484820710000
com.test.videosplayer D/VideoFinder.java: videoPlay_log description = null
com.test.videosplayer D/VideoFinder.java: videoPlay_log duration = 10054
com.test.videosplayer D/VideoFinder.java: videoPlay_log is_private = null
com.test.videosplayer D/VideoFinder.java: videoPlay_log language = null
com.test.videosplayer D/VideoFinder.java: videoPlay_log latitude = null
com.test.videosplayer D/VideoFinder.java: videoPlay_log longitude = null
com.test.videosplayer D/VideoFinder.java: videoPlay_log mini_thumb_magic = 8862884869852541884
com.test.videosplayer D/VideoFinder.java: videoPlay_log resolution = 960x544
com.test.videosplayer D/VideoFinder.java: videoPlay_log tags = null
com.test.videosplayer D/VideoFinder.java: videoPlay_log data = /storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1484820710584.mp4
com.test.videosplayer D/VideoFinder.java: videoPlay_log date_added = 1484820711
com.test.videosplayer D/VideoFinder.java: videoPlay_log date_modified = 1484820710
com.test.videosplayer D/VideoFinder.java: videoPlay_log display_name = wx_camera_1484820710584.mp4
com.test.videosplayer D/VideoFinder.java: videoPlay_log height = 544
com.test.videosplayer D/VideoFinder.java: videoPlay_log width = 960
com.test.videosplayer D/VideoFinder.java: videoPlay_log mime_type = video/mp4
com.test.videosplayer D/VideoFinder.java: videoPlay_log size = 1582483
com.test.videosplayer D/VideoFinder.java: videoPlay_log id = 157942
com.test.videosplayer D/VideoFinder.java: videoPlay_log title = wx_camera_1484820710584
     */
}
