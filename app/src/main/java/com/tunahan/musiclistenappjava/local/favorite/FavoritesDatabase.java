package com.tunahan.musiclistenappjava.local.favorite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorites.class}, version = 1)

public abstract class FavoritesDatabase extends RoomDatabase {
    private static FavoritesDatabase instance;

    public abstract FavoritesDao favoritesDao();

    public static synchronized FavoritesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoritesDatabase.class, "favorites_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
