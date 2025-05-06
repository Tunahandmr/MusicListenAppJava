package com.tunahan.musiclistenappjava.view;

import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tunahan.musiclistenappjava.R;
import com.tunahan.musiclistenappjava.databinding.FragmentListenMusicBinding;
import com.tunahan.musiclistenappjava.local.download.DownloadDao;
import com.tunahan.musiclistenappjava.local.download.DownloadDatabase;
import com.tunahan.musiclistenappjava.local.download.DownloadMusic;
import com.tunahan.musiclistenappjava.local.favorite.Favorites;
import com.tunahan.musiclistenappjava.local.favorite.FavoritesDao;
import com.tunahan.musiclistenappjava.local.favorite.FavoritesDatabase;
import com.tunahan.musiclistenappjava.viewmodel.MainViewModel;

import java.io.File;

public class ListenMusicFragment extends Fragment {
    private FragmentListenMusicBinding binding;
    private ExoPlayer player;
    private ListenMusicFragmentArgs args;
    private DownloadDao musicDao;
    private FavoritesDao favoritesDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        musicDao = DownloadDatabase.getInstance(requireContext()).downloadDao();

        favoritesDao = FavoritesDatabase.getInstance(requireContext()).favoritesDao();
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
        setUpView();
        args = ListenMusicFragmentArgs.fromBundle(getArguments());
        player = new ExoPlayer.Builder(requireContext()).build();

        binding.textViewSongName.setText(args.getSongName());
        binding.textViewArtistName.setText(args.getArtistName());

        binding.backButton.setOnClickListener(v -> {
            exitAnimation();
        });


        MainViewModel viewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(MainViewModel.class);


        viewModel.getNetworkStatus().observe(getViewLifecycleOwner(), isConnected -> {
            if (isConnected) {
                // internet var
                binding.moreButton.setVisibility(View.VISIBLE);
                binding.textViewOffline.setVisibility(View.GONE);
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
                binding.moreButton.setVisibility(View.GONE);
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

    public void addFavorite() {
        Favorites favorites = new Favorites();
        favorites.artistName = args.getArtistName();
        favorites.songName = args.getSongName();
        favorites.imageUrl = args.getImageUrl();
        favorites.songUrl = args.getSongUrl();
        favoritesDao.insertFavorites(favorites);
        Toast.makeText(requireContext(), getString(R.string.added_favorites), Toast.LENGTH_SHORT).show();
    }

    public void deleteFavorite() {
        favoritesDao.deleteFavoriteByName(args.getSongName());
        Toast.makeText(requireContext(), getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show();
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
        musicRequest.setDescription(getString(R.string.music_download));
        musicRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        musicRequest.setDestinationInExternalFilesDir(requireContext(), Environment.DIRECTORY_MUSIC, args.getSongName() + ".mp3");

// Kapak görselini indir (örneğin JPG veya PNG olabilir)
        DownloadManager.Request imageRequest = new DownloadManager.Request(Uri.parse(args.getImageUrl()));
        imageRequest.setTitle(args.getSongName() + " " + getString(R.string.cover_image));
        imageRequest.setDescription(getString(R.string.image_download));
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

        Toast.makeText(requireContext(), args.getSongName() + getString(R.string.successfully_downloaded), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(requireContext(), getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
        }
    }


    private void setUpView() {
        binding.moreButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), binding.moreButton);
            popupMenu.getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());

            if (favoritesDao.isFavorite(args.getSongName())) {
                popupMenu.getMenu().findItem(R.id.favorites).setTitle(getString(R.string.remove_favorites));
            } else {
                popupMenu.getMenu().findItem(R.id.favorites).setTitle(getString(R.string.add_favorites));
            }
            // Menü öğelerine tıklama işlemleri
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.download) {
                    downloadMusic();
                    return true;
                } else if (id == R.id.favorites) {
                    if (favoritesDao.isFavorite(args.getSongName())) {
                        deleteFavorite();
                    } else {
                        addFavorite();
                    }
                    return true;
                } else if (id == R.id.playlist) {
                    ListenMusicFragmentDirections.ActionListenMusicFragmentToAddMusicPlaylistFragment action =
                            ListenMusicFragmentDirections.actionListenMusicFragmentToAddMusicPlaylistFragment(args.getSongUrl(),
                                    args.getImageUrl(),
                                    args.getSongName(),
                                    args.getArtistName());
                    Navigation.findNavController(requireView()).navigate(action);
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });

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