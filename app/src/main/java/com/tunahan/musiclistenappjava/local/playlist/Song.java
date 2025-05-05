package com.tunahan.musiclistenappjava.local.playlist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class Song {
    @PrimaryKey(autoGenerate = true)
    public int songId;

    @NonNull
    public String songName;

    public String playlistName;
    public String artistName;
    public String songUrl;
    public String imageUrl;
}

