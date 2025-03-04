package com.tunahan.musiclistenappjava.service;

import com.tunahan.musiclistenappjava.model.Playlist;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DeezerAPI {
    @GET("playlist/13176167003")
    Observable<Playlist> getDeezer();
}
