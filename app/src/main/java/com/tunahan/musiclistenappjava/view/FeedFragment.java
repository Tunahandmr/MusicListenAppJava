package com.tunahan.musiclistenappjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tunahan.musiclistenappjava.adapter.TrackRecyclerAdapter;
import com.tunahan.musiclistenappjava.databinding.FragmentFeedBinding;
import com.tunahan.musiclistenappjava.model.Playlist;
import com.tunahan.musiclistenappjava.service.DeezerAPI;
import com.tunahan.musiclistenappjava.util.Constants;
import com.tunahan.musiclistenappjava.viewmodel.MainViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedFragment extends Fragment {
    private FragmentFeedBinding binding;
    private Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainViewModel viewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(MainViewModel.class);


        viewModel.getNetworkStatus().observe(getViewLifecycleOwner(), isConnected -> {
            if (isConnected) {
                // internet var
                binding.textViewInternetNotFound.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                Gson gson = new GsonBuilder().setLenient().create();

                retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                loadData();
            } else {
                // internet yok
                binding.textViewInternetNotFound.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void loadData() {
        final DeezerAPI chartAPI = retrofit.create(DeezerAPI.class);
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(chartAPI.getDeezer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));
    }

    private void handleResponse(Playlist playlist) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        TrackRecyclerAdapter trackRecyclerAdapter = new TrackRecyclerAdapter(requireContext(), playlist.getTracks().getData(), (songUrl, imageUrl, songName, artistName) -> {
            FeedFragmentDirections.ActionFeedFragmentToListenMusicFragment action =
                    FeedFragmentDirections.actionFeedFragmentToListenMusicFragment(songUrl, imageUrl, songName, artistName);
            Navigation.findNavController(requireView()).navigate(action);
        });
        binding.recyclerView.setAdapter(trackRecyclerAdapter);
        if (!playlist.getTracks().getData().isEmpty()) {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}