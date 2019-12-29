package com.test.videosplayer.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.test.videosplayer.R;

/**
 * 参考链接：
 * 1. https://blog.csdn.net/smart_mumu/article/details/9130327
 * 2. https://www.jianshu.com/p/4a519531321c
 */
public class AppMainActivity extends AppCompatActivity {

    private final String TAG = "AppMainActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void btnClick(View view) {
        if (view.getId() == R.id.mainAct_btn_local) {
            startActivity(new Intent(this, LocalVideosActivity.class));
        }
    }
}
