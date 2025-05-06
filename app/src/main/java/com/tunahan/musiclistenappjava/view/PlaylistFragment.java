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

import com.tunahan.musiclistenappjava.R;
import com.tunahan.musiclistenappjava.adapter.PlaylistAdapter;
import com.tunahan.musiclistenappjava.databinding.FragmentPlaylistBinding;
import com.tunahan.musiclistenappjava.local.playlist.Playlist;
import com.tunahan.musiclistenappjava.local.playlist.PlaylistDao;
import com.tunahan.musiclistenappjava.local.playlist.PlaylistDatabase;
import com.tunahan.musiclistenappjava.local.playlist.SongDao;
import com.tunahan.musiclistenappjava.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding binding;
    private PlaylistDao playlistDao;
    private SongDao songDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistDao = PlaylistDatabase.getInstance(requireContext()).playlistDao();
        songDao = PlaylistDatabase.getInstance(requireContext()).songDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
    }

    public void setUpView() {
        List<Playlist> playlistList = playlistDao.getPlaylist();
        List<Integer> songCount = new ArrayList<>();
        List<String> playlistImage = new ArrayList<>();
        for (Playlist playlist : playlistList) {
            songCount.add(songDao.getSongsByPlaylistName(playlist.name).size());
            if (songDao.getSongsByPlaylistName(playlist.name).isEmpty()) {
                playlistImage.add(Constants.IMAGE_PLACE_HOLDER);
            } else {
                playlistImage.add(songDao.getSongsByPlaylistName(playlist.name).get(0).imageUrl);
            }
        }
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        PlaylistAdapter playlistAdapter = new PlaylistAdapter(requireContext(), playlistList, songCount, playlistImage, playlistName -> {
            PlaylistFragmentDirections.ActionPlaylistFragmentToPlaylistDetailFragment action =
                    PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(playlistName);
            Navigation.findNavController(requireView()).navigate(action);
        });
        binding.recyclerView.setAdapter(playlistAdapter);

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_playlistFragment_to_createPlaylistFragment);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}