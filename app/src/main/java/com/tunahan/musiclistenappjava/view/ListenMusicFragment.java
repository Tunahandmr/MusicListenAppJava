package com.tunahan.musiclistenappjava.view;

import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.navigation.Navigation;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.bumptech.glide.Glide;
import com.tunahan.musiclistenappjava.databinding.FragmentListenMusicBinding;

public class ListenMusicFragment extends Fragment {
    private FragmentListenMusicBinding binding;

    private ExoPlayer player;
    ListenMusicFragmentArgs args;

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
        args.getImageUrl();
        Glide.with(requireContext())
                .load(args.getImageUrl())
                .into(binding.imageView);
        setupMediaAudio();

        binding.backButton.setOnClickListener(v ->{
            exitAnimation();
        } );
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