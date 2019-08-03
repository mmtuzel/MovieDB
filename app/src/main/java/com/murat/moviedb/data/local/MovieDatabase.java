package com.murat.moviedb.data.local;

import android.content.Context;

import com.murat.moviedb.data.local.dao.MovieDao;
import com.murat.moviedb.data.local.dao.TvDao;
import com.murat.moviedb.data.model.MovieEntity;
import com.murat.moviedb.data.model.TvShowEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieEntity.class, TvShowEntity.class}, version = 2, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static volatile MovieDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public abstract TvDao tvDao();

    public static MovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MovieDatabase.class,
                            "movie_database"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
