package com.tunahan.musiclistenappjava.local.playlist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tunahan.musiclistenappjava.local.favorite.Favorites;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Query("SELECT * FROM playlist")
    List<Playlist> getPlaylist();
    @Insert
    void insertPlaylist(Playlist playlist);

    @Query("DELETE FROM playlist WHERE name = :playlistName")
    void deletePlaylistByName(String playlistName);
}

