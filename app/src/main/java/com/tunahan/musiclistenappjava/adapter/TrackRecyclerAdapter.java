package com.tunahan.musiclistenappjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tunahan.musiclistenappjava.databinding.TrackRowBinding;
import com.tunahan.musiclistenappjava.model.Track;
import java.util.List;

public class TrackRecyclerAdapter extends RecyclerView.Adapter<TrackRecyclerAdapter.TrackViewHolder> {

    private List<Track> trackList;
    private final Context context;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(String songUrl, String imageUrl);
    }

    public TrackRecyclerAdapter(Context context, List<Track> trackList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.trackList = trackList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TrackRowBinding binding = TrackRowBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TrackViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackRecyclerAdapter.TrackViewHolder holder, int position) {
        Track currentTrack = trackList.get(position);
        holder.binding.textViewSongName.setText(currentTrack.getTitle());
        holder.binding.textViewArtistName.setText(currentTrack.getArtist().getName());

        String imageUrl = currentTrack.getAlbum().getCoverBig();
        if (imageUrl!=null) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.binding.imageViewSongImage);
        }

        holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(currentTrack.getPreview(), imageUrl));
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {

        private final TrackRowBinding binding;

        public TrackViewHolder(TrackRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateData(List<Track> trackList){
        this.trackList = trackList;
        notifyDataSetChanged();
    }
}
