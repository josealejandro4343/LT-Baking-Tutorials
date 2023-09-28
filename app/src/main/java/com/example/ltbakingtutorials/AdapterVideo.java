package com.example.ltbakingtutorials;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.HolderVideo> {

    private Context context;
    private ArrayList<ModelVideo> videoArrayList;

    public AdapterVideo(Context context, ArrayList<ModelVideo> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public HolderVideo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_video, parent, false);
        return new HolderVideo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderVideo holder, int position) {
        ModelVideo modelVideo = videoArrayList.get(position);
        String recipeName = modelVideo.getRecipeName();
        String videoUrl = modelVideo.getVideo();
        String credits = modelVideo.getCredits();

        holder.titleView.setText(recipeName);
        holder.titleTxtV2.setText(credits);
        setVideoUrl(modelVideo, holder);
    }

    private void setVideoUrl(ModelVideo modelVideo, HolderVideo holder) {

        String videoUrl = modelVideo.getVideo();

        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.videoView);

        Uri videoUri = Uri.parse(videoUrl);
        holder.videoView.setMediaController(mediaController);
        holder.videoView.setVideoURI(videoUri);

        holder.videoView.requestFocus();
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
            }
        });

        holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END: {
                        holder.progressBarVideo.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    class HolderVideo extends RecyclerView.ViewHolder {

        VideoView videoView;
        TextView titleView, titleTxtV2;
        ProgressBar progressBarVideo;

        public HolderVideo(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            titleView = itemView.findViewById(R.id.titleTxtV);
            titleTxtV2 = itemView.findViewById(R.id.titleTxtV2);
            progressBarVideo = itemView.findViewById(R.id.progressbar_video);
        }
    }
}
