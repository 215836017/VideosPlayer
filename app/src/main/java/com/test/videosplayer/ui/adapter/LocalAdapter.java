package com.test.videosplayer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.test.videosplayer.R;
import com.test.videosplayer.utils.LogUtil;
import com.test.videosplayer.video.VideoBean;

import java.util.List;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {

    private final String TAG = "LocalAdapter.java";

    private Context context;
    private List<VideoBean> vides;
    private String strDir = "";

    public LocalAdapter(Context context, List<VideoBean> vides) {
        this.context = context;
        this.vides = vides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_local_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {

        VideoBean videoBean = vides.get(pos);
        String[] videoDirs = videoBean.getData().split("/");
        String dir = videoDirs[videoDirs.length - 2];

        LogUtil.i(TAG, "onBindViewHolder() -- strDir = " + strDir + ", dir = " + dir);
        if (strDir.equals(dir)) {
            LogUtil.i(TAG, "onBindViewHolder() 1111111111111");
            viewHolder.textDir.setVisibility(View.GONE);

        } else {
            LogUtil.i(TAG, "onBindViewHolder() 22222222222");
            viewHolder.textDir.setVisibility(View.VISIBLE);
            viewHolder.textDir.setText(dir);
            strDir = dir;
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
