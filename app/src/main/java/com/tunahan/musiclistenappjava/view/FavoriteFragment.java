package com.tunahan.musiclistenappjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tunahan.musiclistenappjava.adapter.FavoriteAdapter;
import com.tunahan.musiclistenappjava.databinding.FragmentFavoriteBinding;
import com.tunahan.musiclistenappjava.local.favorite.Favorites;
import com.tunahan.musiclistenappjava.local.favorite.FavoritesDao;
import com.tunahan.musiclistenappjava.local.favorite.FavoritesDatabase;

import java.util.List;


public class FavoriteFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private FavoriteAdapter favoriteAdapter;
    private FavoritesDao favoritesDao;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoritesDao = FavoritesDatabase.getInstance(requireContext()).favoritesDao();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
    }

    private void setUpView() {
        List<Favorites> favoritesList = favoritesDao.getFavorites();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        favoriteAdapter = new FavoriteAdapter(requireContext(), favoritesList, (songUrl, imageUrl, songName, artistName) -> {
            FavoriteFragmentDirections.ActionFavoriteFragmentToListenMusicFragment action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToListenMusicFragment(songUrl, imageUrl, songName, artistName);
            Navigation.findNavController(requireView()).navigate(action);
        }, songName -> {
            favoritesDao.deleteFavoriteByName(songName);
            favoriteAdapter.updateData(favoritesDao.getFavorites());
        });
        binding.recyclerView.setAdapter(favoriteAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}