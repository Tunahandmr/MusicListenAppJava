package com.tunahan.musiclistenappjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tunahan.musiclistenappjava.R;
import com.tunahan.musiclistenappjava.databinding.PlaylistRowBinding;
import com.tunahan.musiclistenappjava.local.playlist.Playlist;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private List<Playlist> playlistList;

    private List<Integer> songCountList;

    private List<String> playlistImageList;
    private final Context context;

    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(String playlistName);
    }

    public PlaylistAdapter(Context context, List<Playlist> playlistList, List<Integer> songCountList,
                           List<String> playlistImageList,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.playlistList = playlistList;
        this.songCountList = songCountList;
        this.playlistImageList = playlistImageList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PlaylistAdapter.PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlaylistRowBinding binding = PlaylistRowBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PlaylistAdapter.PlaylistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.PlaylistViewHolder holder, int position) {
        Playlist currentPlaylist = playlistList.get(position);
        holder.binding.textViewPlaylistName.setText(currentPlaylist.name);
        holder.binding.textViewSongCount.setText(songCountList.get(position) + " " + context.getString(R.string.music));

        String imageUrl = playlistImageList.get(position);
        if (imageUrl != null) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.binding.imageViewSongImage);
        }

        holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(currentPlaylist.name));
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {

        private final PlaylistRowBinding binding;

        public PlaylistViewHolder(PlaylistRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
