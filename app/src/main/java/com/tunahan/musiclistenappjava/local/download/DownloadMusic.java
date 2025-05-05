package com.tunahan.musiclistenappjava.local.download;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "download_music")
public class DownloadMusic {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String songName;
    public String artistName;
    public String imageUrl;
    public String songUrl;
}
