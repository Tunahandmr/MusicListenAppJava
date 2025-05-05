package com.tunahan.musiclistenappjava.local.favorite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    List<Favorites> getFavorites();

    @Insert
    void insertFavorites(Favorites favorites);

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE songName = :musicName)")
    boolean isFavorite(String musicName);

    @Query("DELETE FROM favorites WHERE songName = :musicName")
    void deleteFavoriteByName(String musicName);

}
