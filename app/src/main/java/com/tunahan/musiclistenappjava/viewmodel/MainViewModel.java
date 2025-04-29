package com.tunahan.musiclistenappjava.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tunahan.musiclistenappjava.util.NetworkUtils;

public class MainViewModel extends AndroidViewModel {
    private final NetworkUtils networkUtils;

    public MainViewModel(Application application) {
        super(application);
        networkUtils = new NetworkUtils(application.getApplicationContext());
    }

    public LiveData<Boolean> getNetworkStatus() {
        return networkUtils;
    }
}
