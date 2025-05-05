package com.tunahan.musiclistenappjava.local.playlist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongDao {
    @Insert
    void insertSong(Song song);

    @Query("SELECT * FROM Song WHERE playlistName = :playlistName")
    List<Song> getSongsByPlaylistName(String playlistName);

    @Query("DELETE FROM song WHERE songName = :songName")
    void deleteSongByName(String songName);
}
