package com.tunahan.musiclistenappjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tunahan.musiclistenappjava.R;
import com.tunahan.musiclistenappjava.databinding.FragmentCreatePlaylistBinding;
import com.tunahan.musiclistenappjava.local.playlist.Playlist;
import com.tunahan.musiclistenappjava.local.playlist.PlaylistDao;
import com.tunahan.musiclistenappjava.local.playlist.PlaylistDatabase;

import java.util.Objects;

public class CreatePlaylistFragment extends Fragment {
    private FragmentCreatePlaylistBinding binding;
    private PlaylistDao playlistDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistDao = PlaylistDatabase.getInstance(requireContext()).playlistDao();
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
    }

    private void setUpView(){
        binding.imageViewBack.setOnClickListener(v->{
            Navigation.findNavController(v).popBackStack();
        });
        binding.cancelButton.setOnClickListener(v->{
            Navigation.findNavController(v).popBackStack();
        });
        binding.saveButton.setOnClickListener(v->{
            insertPlaylist();
        });
    }

    private void insertPlaylist(){
        Playlist playlist = new Playlist();
        playlist.name = Objects.requireNonNull(Objects.requireNonNull(binding.playlistNameET).getText()).toString();
        playlistDao.insertPlaylist(playlist);

        Toast.makeText(requireContext(),getString(R.string.successfully_registered),Toast.LENGTH_LONG).show();
        Navigation.findNavController(requireView()).popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}