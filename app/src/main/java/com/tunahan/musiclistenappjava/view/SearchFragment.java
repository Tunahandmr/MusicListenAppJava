package com.tunahan.musiclistenappjava.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tunahan.musiclistenappjava.adapter.TrackRecyclerAdapter;
import com.tunahan.musiclistenappjava.databinding.FragmentSearchBinding;
import com.tunahan.musiclistenappjava.model.Track;
import com.tunahan.musiclistenappjava.model.TrackCollection;
import com.tunahan.musiclistenappjava.service.SearchAPI;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;
    private TrackRecyclerAdapter trackRecyclerAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.deezer.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        setupView();
    }

    private void searchData(String query) {
        final SearchAPI searchAPI = retrofit.create(SearchAPI.class);
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(searchAPI.getSearch(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::searchHandleResponse));
    }
    private void setupView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        trackRecyclerAdapter = new TrackRecyclerAdapter(requireContext(), new ArrayList<Track>(), (songUrl, imageUrl, songName, artistName) -> {
            SearchFragmentDirections.ActionSearchFragmentToListenMusicFragment action =
                    SearchFragmentDirections.actionSearchFragmentToListenMusicFragment(songUrl, imageUrl, songName, artistName);
            Navigation.findNavController(requireView()).navigate(action);
        });
        binding.recyclerView.setAdapter(trackRecyclerAdapter);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    searchData(newText);
                }
                return true;
            }
        });
    }

    private void searchHandleResponse(TrackCollection trackCollection) {
        trackRecyclerAdapter.updateData(trackCollection.getData());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}