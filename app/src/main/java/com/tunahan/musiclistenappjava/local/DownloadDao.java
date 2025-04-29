package com.tunahan.musiclistenappjava.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DownloadDao {
    @Query("SELECT * FROM download_music")
    List<DownloadMusic> getMusic();

    @Insert
    void insertMusic(DownloadMusic downloadMusic);
}
