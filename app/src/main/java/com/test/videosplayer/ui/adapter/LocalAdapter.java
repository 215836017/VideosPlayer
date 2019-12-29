package com.test.videosplayer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.test.videosplayer.MediaCoverHelper;
import com.test.videosplayer.R;
import com.test.videosplayer.ui.PlayerActivity;
import com.test.videosplayer.utils.LogUtil;
import com.test.videosplayer.video.VideoBean;

import java.util.List;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {

    private final String TAG = "LocalAdapter.java";

    public static final String EXTRA_VIDEO_INFO = "videoInfo";

    private Context context;
    private List<VideoBean> vides;
    private String strDir;
    private Intent intent = new Intent();
    private MediaCoverHelper mediaCoverHelper;

    public LocalAdapter(Context context, List<VideoBean> vides) {
        this.context = context;
        this.vides = vides;
        mediaCoverHelper = new MediaCoverHelper();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_local_list, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {

        final VideoBean videoBean = vides.get(pos);
        LogUtil.i(TAG, "onBindViewHolder() -- videoBean: " + videoBean.toString());
        String[] videoDirs = videoBean.getData().split("/");
        String dir = videoDirs[videoDirs.length - 2];
        String path = "/storage/emulated/0/AVC264.mp4";
        viewHolder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(context, PlayerActivity.class);
                intent.putExtra(EXTRA_VIDEO_INFO, videoBean);
                context.startActivity(intent);
            }
        });

        LogUtil.i(TAG, "onBindViewHolder() -- strDir = " + strDir + ", dir = " + dir);
        if (TextUtils.isEmpty(strDir) || !strDir.equals(dir)) {
            LogUtil.i(TAG, "onBindViewHolder() 1111111111111");
            viewHolder.textDir.setVisibility(View.VISIBLE);
            viewHolder.textDir.setText(dir);
            strDir = dir;
        } else {
            LogUtil.i(TAG, "onBindViewHolder() 22222222222");
            viewHolder.textDir.setVisibility(View.GONE);
        }
        Bitmap videoCover = mediaCoverHelper.getVideoCover(videoBean.getData());
        if (null != videoCover) {
            LogUtil.i(TAG, "onBindViewHolder() 3333333");
            viewHolder.imageAlbum.setImageBitmap(videoCover);
        } else {
            LogUtil.i(TAG, "onBindViewHolder() 444444444444");
            viewHolder.imageAlbum.setImageResource(R.mipmap.ic_launcher_round);
        }

        viewHolder.imageAlbum.setImageResource(R.mipmap.ic_launcher_round);
        viewHolder.textSize.setText(videoBean.getSize() + "KB");
        viewHolder.textTitle.setText(videoBean.getDisplay_name());

    }

    @Override
    public int getItemCount() {
        return vides.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textDir;
        private ConstraintLayout layoutRoot;
        private ImageView imageAlbum;
        private TextView textTitle;
        private TextView textSize;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textDir = itemView.findViewById(R.id.itemLocal_text_dir);
            this.layoutRoot = itemView.findViewById(R.id.itemLocal_layout_root);
            this.imageAlbum = itemView.findViewById(R.id.itemLocal_image_album);
            this.textTitle = itemView.findViewById(R.id.itemLocal_text_title);
            this.textSize = itemView.findViewById(R.id.itemLocal_text_size);
        }
    }
}
