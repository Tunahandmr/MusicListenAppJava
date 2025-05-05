package com.tunahan.musiclistenappjava.local.playlist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tunahan.musiclistenappjava.local.favorite.FavoritesDao;
import com.tunahan.musiclistenappjava.local.favorite.FavoritesDatabase;

@Database(entities = {Playlist.class, Song.class}, version = 1)
public abstract class PlaylistDatabase extends RoomDatabase {
    private static PlaylistDatabase instance;

    public abstract PlaylistDao playlistDao();
    public abstract SongDao songDao();

    public static synchronized PlaylistDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            PlaylistDatabase.class, "playlist_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}