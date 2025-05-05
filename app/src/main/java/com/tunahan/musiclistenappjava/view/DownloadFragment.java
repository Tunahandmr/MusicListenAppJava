package com.tunahan.musiclistenappjava.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tunahan.musiclistenappjava.adapter.DownloadAdapter;
import com.tunahan.musiclistenappjava.databinding.FragmentDownloadBinding;
import com.tunahan.musiclistenappjava.local.download.DownloadDao;
import com.tunahan.musiclistenappjava.local.download.DownloadDatabase;
import com.tunahan.musiclistenappjava.local.download.DownloadMusic;
import java.util.List;

public class DownloadFragment extends Fragment {

    private FragmentDownloadBinding binding;
    private RecyclerView recyclerView;
    private DownloadDao downloadDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadDao = DownloadDatabase.getInstance(requireContext()).downloadDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDownloadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = binding.downloadRecyclerView;
        getData();
    }

    private void getData() {
        binding.downloadRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<DownloadMusic> musicList = downloadDao.getMusic();
        DownloadAdapter downloadAdapter = new DownloadAdapter(requireContext(), musicList, (songUrl, imageUrl, songName, artistName) -> {

            DownloadFragmentDirections.ActionDownloadFragmentToListenMusicFragment action =
                    DownloadFragmentDirections.actionDownloadFragmentToListenMusicFragment(songUrl, imageUrl, songName, artistName);

            Navigation.findNavController(requireView()).navigate(action);
        });
        recyclerView.setAdapter(downloadAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}