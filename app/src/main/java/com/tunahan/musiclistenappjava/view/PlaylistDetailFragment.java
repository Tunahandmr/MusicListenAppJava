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

import com.tunahan.musiclistenappjava.adapter.PlaylistDetailAdapter;
import com.tunahan.musiclistenappjava.databinding.FragmentPlaylistDetailBinding;
import com.tunahan.musiclistenappjava.local.playlist.PlaylistDatabase;
import com.tunahan.musiclistenappjava.local.playlist.Song;
import com.tunahan.musiclistenappjava.local.playlist.SongDao;

import java.util.List;


public class PlaylistDetailFragment extends Fragment {

    private FragmentPlaylistDetailBinding binding;
    private SongDao songDao;

    private PlaylistDetailFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songDao = PlaylistDatabase.getInstance(requireContext()).songDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = PlaylistDetailFragmentArgs.fromBundle(getArguments());
        setupView();
    }

    private void setupView() {
        binding.textViewPlaylistName.setText(args.getPlaylistName());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Song> musicList = songDao.getSongsByPlaylistName(args.getPlaylistName());
        PlaylistDetailAdapter playlistDetailAdapter = new PlaylistDetailAdapter(requireContext(), musicList, (songUrl, imageUrl, songName, artistName) -> {

            PlaylistDetailFragmentDirections.ActionPlaylistDetailFragmentToListenMusicFragment action =
                    PlaylistDetailFragmentDirections.actionPlaylistDetailFragmentToListenMusicFragment(songUrl, imageUrl, songName, artistName);

            Navigation.findNavController(requireView()).navigate(action);
        });
        binding.recyclerView.setAdapter(playlistDetailAdapter);

        binding.imageViewBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}