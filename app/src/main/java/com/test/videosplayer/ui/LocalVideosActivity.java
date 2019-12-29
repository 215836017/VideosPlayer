package com.test.videosplayer.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.test.videosplayer.R;
import com.test.videosplayer.ui.adapter.LocalAdapter;
import com.test.videosplayer.utils.LogUtil;
import com.test.videosplayer.video.VideoBean;
import com.test.videosplayer.video.VideoFinder;

import java.util.List;

public class LocalVideosActivity extends AppCompatActivity {

    private final String TAG = "LocalVideosActivity";

    private VideoFinder videoFinder;
    private List<VideoBean> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_videos);

        initDatas();
        initViews();
    }

    private void initDatas() {
        videoFinder = new VideoFinder();
        videoList = videoFinder.getAllVideos(this);
    }

    private void initViews() {

        LogUtil.i(TAG, "initViews() -- videoList.size() = " + videoList.size());
        RecyclerView recyclerView = findViewById(R.id.localAct_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LocalAdapter adapter = new LocalAdapter(this, videoList);
        recyclerView.setAdapter(adapter);
    }
}
