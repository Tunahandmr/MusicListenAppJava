package com.tunahan.musiclistenappjava.local.favorite;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorites {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String songName;
    public String artistName;
    public String imageUrl;
    public String songUrl;
}
