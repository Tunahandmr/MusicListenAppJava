package com.tunahan.musiclistenappjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tunahan.musiclistenappjava.databinding.FavoriteRowBinding;
import com.tunahan.musiclistenappjava.local.favorite.Favorites;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorites> favoritesList;

    private final Context context;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(String songUrl, String imageUrl, String songName, String artistName);
    }

    private final DeleteFavListener deleteFavListener;

    public interface DeleteFavListener {
        void DeleteFav(String songName);
    }

    public FavoriteAdapter(Context context, List<Favorites> favoritesList, FavoriteAdapter.OnItemClickListener onItemClickListener,
                           DeleteFavListener deleteFavListener) {
        this.context = context;
        this.favoritesList = favoritesList;
        this.onItemClickListener = onItemClickListener;
        this.deleteFavListener = deleteFavListener;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavoriteRowBinding binding = FavoriteRowBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        Favorites currentFavorites = favoritesList.get(position);
        holder.binding.textViewSongName.setText(currentFavorites.songName);
        holder.binding.textViewArtistName.setText(currentFavorites.artistName);

        String imageUrl = currentFavorites.imageUrl;
        if (imageUrl != null) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.binding.imageViewSongImage);
        }

        holder.binding.imageViewFav.setOnClickListener(v -> deleteFavListener.DeleteFav(currentFavorites.songName));
        holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(currentFavorites.songUrl, currentFavorites.imageUrl, currentFavorites.songName, currentFavorites.artistName));

    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private final FavoriteRowBinding binding;

        public FavoriteViewHolder(FavoriteRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateData(List<Favorites> favoritesList) {
        this.favoritesList = favoritesList;
        notifyDataSetChanged();
    }
}
