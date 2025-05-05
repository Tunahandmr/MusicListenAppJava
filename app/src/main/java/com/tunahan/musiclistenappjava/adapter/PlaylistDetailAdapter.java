package com.tunahan.musiclistenappjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tunahan.musiclistenappjava.databinding.TrackRowBinding;
import com.tunahan.musiclistenappjava.local.playlist.Song;
import com.tunahan.musiclistenappjava.model.Track;

import java.util.List;

public class PlaylistDetailAdapter extends RecyclerView.Adapter<PlaylistDetailAdapter.PlaylistDetailViewHolder>{
    private List<Song> songList;
    private final Context context;
    private final PlaylistDetailAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(String songUrl, String imageUrl, String songName, String artistName);
    }

    public PlaylistDetailAdapter(Context context, List<Song> songList, PlaylistDetailAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.songList = songList;
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public PlaylistDetailAdapter.PlaylistDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TrackRowBinding binding = TrackRowBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PlaylistDetailAdapter.PlaylistDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistDetailAdapter.PlaylistDetailViewHolder holder, int position) {
        Song currentSong = songList.get(position);
        holder.binding.textViewSongName.setText(currentSong.songName);
        holder.binding.textViewArtistName.setText(currentSong.artistName);

        String imageUrl = currentSong.imageUrl;
        if (imageUrl != null) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.binding.imageViewSongImage);
        }

        holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(currentSong.songUrl, imageUrl, currentSong.songName, currentSong.artistName));
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class PlaylistDetailViewHolder extends RecyclerView.ViewHolder {

        private final TrackRowBinding binding;

        public PlaylistDetailViewHolder(TrackRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
