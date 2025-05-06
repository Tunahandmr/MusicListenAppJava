package com.tunahan.musiclistenappjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tunahan.musiclistenappjava.R;
import com.tunahan.musiclistenappjava.databinding.FragmentAddMusicPlaylistBinding;
import com.tunahan.musiclistenappjava.local.playlist.Playlist;
import com.tunahan.musiclistenappjava.local.playlist.PlaylistDao;
import com.tunahan.musiclistenappjava.local.playlist.PlaylistDatabase;
import com.tunahan.musiclistenappjava.local.playlist.Song;
import com.tunahan.musiclistenappjava.local.playlist.SongDao;

public class AddMusicPlaylistFragment extends Fragment {
    private FragmentAddMusicPlaylistBinding binding;
    private PlaylistDao playlistDao;
    private SongDao songDao;
    private AddMusicPlaylistFragmentArgs args;

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
        binding = FragmentAddMusicPlaylistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = AddMusicPlaylistFragmentArgs.fromBundle(getArguments());
        setupView();
    }

    private void setupView() {
        binding.createPlaylistButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_addMusicPlaylistFragment_to_createPlaylistFragment));

        if (playlistDao.getPlaylist().isEmpty()) {
            binding.cancelButton.setVisibility(View.GONE);
            binding.saveButton.setVisibility(View.GONE);
        } else {
            for (Playlist playlist : playlistDao.getPlaylist()) {
                RadioButton radioButton = new RadioButton(requireContext());
                radioButton.setText(playlist.name);
                radioButton.setId(View.generateViewId());
                radioButton.setTextSize(16);
                binding.radioGroup.addView(radioButton);
            }
        }

        binding.saveButton.setOnClickListener(v -> {
            Song song = new Song();
            song.songName = args.getSongName();
            song.songUrl = args.getSongUrl();
            song.artistName = args.getArtistName();
            song.imageUrl = args.getImageUrl();
            int selectedId = binding.radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) { // Bir şey seçildiyse
                RadioButton selected = binding.radioGroup.findViewById(selectedId);
                song.playlistName = selected.getText().toString();
            }
            songDao.insertSong(song);

            Toast.makeText(requireContext(), getString(R.string.successfully_registered), Toast.LENGTH_LONG).show();
            Navigation.findNavController(v).popBackStack();
        });

        binding.cancelButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        binding.imageViewBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}