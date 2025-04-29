package com.tunahan.musiclistenappjava.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class NetworkUtils extends LiveData<Boolean> {

//    public static boolean isOnline(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager)
//                context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (connectivityManager == null)
//            return false;
//
//        Network network = connectivityManager.getActiveNetwork();
//        if (network == null)
//            return false;
//
//        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
//        return capabilities != null &&
//                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
//                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
//                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
//    }

    private final ConnectivityManager connectivityManager;
    private final ConnectivityManager.NetworkCallback networkCallback;

    public NetworkUtils(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                postValue(true); // internet var
            }

            @Override
            public void onLost(@NonNull Network network) {
                postValue(false); // internet yok
            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();

        NetworkRequest request = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();

        connectivityManager.registerNetworkCallback(request, networkCallback);

        // Başlangıçta bağlantı durumunu kontrol et
        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        boolean isConnected = capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        postValue(isConnected);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}

