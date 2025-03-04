package com.tunahan.musiclistenappjava.service;


import com.tunahan.musiclistenappjava.model.TrackCollection;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchAPI {
    @GET("search")
    Observable<TrackCollection> getSearch(@Query("q") String query);
}
