package com.tunahan.musiclistenappjava.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DownloadMusic.class}, version = 1)
public abstract class DownloadDatabase extends RoomDatabase {
    private static DownloadDatabase instance;

    public abstract DownloadDao downloadDao();

    public static synchronized DownloadDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            DownloadDatabase.class, "music_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
