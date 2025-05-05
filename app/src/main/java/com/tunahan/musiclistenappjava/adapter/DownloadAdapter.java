package com.tunahan.musiclistenappjava.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tunahan.musiclistenappjava.databinding.TrackRowBinding;
import com.tunahan.musiclistenappjava.local.download.DownloadMusic;

import java.io.File;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder> {

    private List<DownloadMusic> downloadMusicList;
    private final Context context;
    private final DownloadAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(String songUrl, String imageUrl, String songName, String artistName);
    }

    public DownloadAdapter(Context context, List<DownloadMusic> trackList, DownloadAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.downloadMusicList = trackList;
        this.onItemClickListener = onItemClickListener;
    }

    public static class DownloadViewHolder extends RecyclerView.ViewHolder {

        private final TrackRowBinding binding;

        public DownloadViewHolder(TrackRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public DownloadAdapter.DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TrackRowBinding binding = TrackRowBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new DownloadAdapter.DownloadViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.DownloadViewHolder holder, int position) {
        DownloadMusic currentDownloadMusic = downloadMusicList.get(position);
        holder.binding.textViewSongName.setText(currentDownloadMusic.songName);
        holder.binding.textViewArtistName.setText(currentDownloadMusic.artistName);

        holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(currentDownloadMusic.songUrl, currentDownloadMusic.imageUrl, currentDownloadMusic.songName, currentDownloadMusic.artistName));

        File imageFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), currentDownloadMusic.songName + "_cover.jpg");
        if (imageFile.exists()) {
            Glide.with(context)
                    .load(imageFile)
                    .into(holder.binding.imageViewSongImage);
        }
    }

    @Override
    public int getItemCount() {
        return downloadMusicList.size();
    }
}
