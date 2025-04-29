package com.tunahan.musiclistenappjava.view;

import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.os.Environment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tunahan.musiclistenappjava.databinding.FragmentListenMusicBinding;
import com.tunahan.musiclistenappjava.local.DownloadDao;
import com.tunahan.musiclistenappjava.local.DownloadDatabase;
import com.tunahan.musiclistenappjava.local.DownloadMusic;
import com.tunahan.musiclistenappjava.viewmodel.MainViewModel;

import java.io.File;

public class ListenMusicFragment extends Fragment {
    private FragmentListenMusicBinding binding;

    private ExoPlayer player;
    private ListenMusicFragmentArgs args;
    private DownloadDatabase db;
    private DownloadDao musicDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(requireContext(),
                        DownloadDatabase.class, "music-db")
                .allowMainThreadQueries()
                .build();

        musicDao = db.downloadDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListenMusicBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startAnimation();
        args = ListenMusicFragmentArgs.fromBundle(getArguments());
        player = new ExoPlayer.Builder(requireContext()).build();

        binding.backButton.setOnClickListener(v -> {
            exitAnimation();
        });

        binding.downloadButton.setOnClickListener(v -> {
            downloadMusic();
        });

        MainViewModel viewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(MainViewModel.class);


        viewModel.getNetworkStatus().observe(getViewLifecycleOwner(), isConnected -> {
            if (isConnected) {
                // internet var
                Glide.with(requireContext())
                        .load(args.getImageUrl())
                        .into(binding.imageView);
                setupMediaAudio();
            } else {
                // internet yok
                File imageFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), args.getSongName() + "_cover.jpg");
                if (imageFile.exists()) {
                    Glide.with(requireContext())
                            .load(imageFile)
                            .into(binding.imageView);
                }
                binding.textViewOffline.setVisibility(View.VISIBLE);
                binding.downloadButton.setVisibility(View.GONE);
                playOfflineMusic();
            }
        });

    }

    private void setupMediaAudio() {
        binding.playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(args.getSongUrl()));
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    public void startAnimation() {
        int screenHeight = getScreenHeight();

        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.getRoot(), "translationY", screenHeight, 0);
        animator.setDuration(500); // 500ms sürede hareket et
        animator.setInterpolator(new AccelerateDecelerateInterpolator()); // Akıcı geçiş
        animator.start();
    }

    public void exitAnimation() {
        int screenHeight = getScreenHeight();

        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.getRoot(), "translationY", screenHeight);
        animator.setDuration(500); // 500ms sürede hareket et
        animator.setInterpolator(new AccelerateDecelerateInterpolator()); // Akıcı geçiş
        new android.os.Handler(Looper.getMainLooper()).postDelayed(() -> {
            Navigation.findNavController(requireView()).popBackStack();
        }, 400);
        animator.start();
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public void downloadMusic() {
        // Müzik dosyasını indir
        DownloadManager.Request musicRequest = new DownloadManager.Request(Uri.parse(args.getSongUrl()));
        musicRequest.setTitle(args.getSongName());
        musicRequest.setDescription("Müzik indiriliyor...");
        musicRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        musicRequest.setDestinationInExternalFilesDir(requireContext(), Environment.DIRECTORY_MUSIC, args.getSongName() + ".mp3");

// Kapak görselini indir (örneğin JPG veya PNG olabilir)
        DownloadManager.Request imageRequest = new DownloadManager.Request(Uri.parse(args.getImageUrl()));
        imageRequest.setTitle(args.getSongName() + " Kapak Görseli");
        imageRequest.setDescription("Görsel indiriliyor...");
        imageRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        imageRequest.setDestinationInExternalFilesDir(requireContext(), Environment.DIRECTORY_PICTURES, args.getSongName() + "_cover.jpg");

        DownloadManager downloadManager = (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(musicRequest);
            downloadManager.enqueue(imageRequest);
        }

        DownloadMusic music = new DownloadMusic();
        music.artistName = args.getArtistName();
        music.songName = args.getSongName();
        music.imageUrl = args.getImageUrl();
        music.songUrl = args.getSongUrl();
        musicDao.insertMusic(music);

        Toast.makeText(requireContext(), "Müzik indirildi ve kaydedildi", Toast.LENGTH_SHORT).show();
    }

    public void playOfflineMusic() {
        binding.playerView.setPlayer(player);
        // Dışa kaydedilen müzik dosyasının yolu
        File file = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), args.getSongName() + ".mp3");

// Dosya var mı diye kontrol et
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        } else {
            Toast.makeText(requireContext(), "Dosya bulunamadı", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.release();
            player = null;
        }
        binding = null;
    }
}