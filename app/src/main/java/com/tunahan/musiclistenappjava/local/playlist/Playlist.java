package com.tunahan.musiclistenappjava.local.playlist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;


@Entity
public class Playlist {
    @PrimaryKey(autoGenerate = true)
    public int playlistId;

    @NonNull
    public String name;
}

