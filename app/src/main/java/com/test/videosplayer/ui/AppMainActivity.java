package com.test.videosplayer.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.videosplayer.R;
import com.test.videosplayer.ui.adapter.LocalAdapter;
import com.test.videosplayer.utils.LogUtil;
import com.test.videosplayer.video.VideoBean;
import com.test.videosplayer.video.VideoFinder;

import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class AppMainActivity extends AppCompatActivity {

    private final String TAG = "AppMainActivity";

    private final int CODE_REQUEST_ALL_PERMISSIONS = 0x10;
    private AlertDialog alertDialog;
    private final String[] requestPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private TextView textEmpty;
    private RecyclerView recyclerView;

    private ProgressBar progressBar;
    private VideoFinder videoFinder;
    private List<VideoBean> videosList;

    private final int MSG_QUERY_VIDEO_FAILED = 0x10;
    private final int MSG_QUERY_VIDEO_SUCC = 0x11;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_QUERY_VIDEO_FAILED:
                    progressBar.setVisibility(View.GONE);
                    textEmpty.setVisibility(View.GONE);
                    break;

                case MSG_QUERY_VIDEO_SUCC:
                    progressBar.setVisibility(View.GONE);
                    setDatas();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        if (isAllPermissionsApplyed()) {
            queryAllVideos();
        } else {
            applyAllPermissions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isAllPermissionsApplyed() {

        for (String permission : requestPermissions) {
            if (!hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasPermission(String permission) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, permission) != PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void applyAllPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 一次性申请需要的所有权限
            ActivityCompat.requestPermissions(this, requestPermissions, CODE_REQUEST_ALL_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtil.d(TAG, "onRequestPermissionsResult() -- permissions.len = " + permissions.length
                + ", grantResults.len = " + grantResults.length);

        switch (requestCode) {
            case CODE_REQUEST_ALL_PERMISSIONS:
                handleAllPermissions(permissions, grantResults);
                break;
        }
    }

    private void handleAllPermissions(String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                // 选择了“允许”
                // Toast.makeText(this, "权限：" + permissions[i] + "申请成功", Toast.LENGTH_LONG).show();
                queryAllVideos();

            } else {   //选择禁止,
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {  // 选择禁止, 但没有勾选不再询问

                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    builder.setTitle("permission")
                            .setMessage("点击允许才可以继续使用！")
                            .setPositiveButton("去允许", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    if (alertDialog != null && alertDialog.isShowing()) {
                                        alertDialog.dismiss();
                                    }
                                    applyAllPermissions();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (null != alertDialog && alertDialog.isShowing()) {
                                        alertDialog.dismiss();
                                    }
                                }
                            });
                    alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
            }
        }
    }

    private void initViews() {
        textEmpty = findViewById(R.id.main_activity_tv_empty);
        recyclerView = findViewById(R.id.main_activity_rv);
        progressBar = findViewById(R.id.main_activity_pb);
    }

    public void mainActTextClick(View view) {
        if (view.getId() == R.id.main_activity_tv_back) {
            finish();

        } else if (view.getId() == R.id.main_activity_tv_history) {
            // todo: show watch history list.
        }
    }

    private void queryAllVideos() {
        progressBar.setVisibility(View.VISIBLE);
        videoFinder = new VideoFinder(this, queryVideosListener);
        videoFinder.start();
    }

    private void setDatas() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LocalAdapter adapter = new LocalAdapter(this, videosList);
        recyclerView.setAdapter(adapter);
    }

    VideoFinder.OnQueryVideosListener queryVideosListener = new VideoFinder.OnQueryVideosListener() {
        @Override
        public void onQueryVideosFinish(List<VideoBean> list) {
            if (null != list && list.size() > 0) {
                videosList = list;
                handler.sendEmptyMessage(MSG_QUERY_VIDEO_SUCC);

            } else {
                handler.sendEmptyMessage(MSG_QUERY_VIDEO_FAILED);
            }
        }
    };
}
