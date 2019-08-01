package com.murat.moviedb.data.local.dao;

import com.murat.moviedb.data.model.MovieEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies WHERE movieType = 0")
    LiveData<List<MovieEntity>> loadAllTopRatedMovies();

    @Query("SELECT * FROM movies WHERE movieType = 1")
    LiveData<List<MovieEntity>> loadNowPlayingMovies();

    @Query("SELECT * FROM movies WHERE movieType = 2")
    LiveData<List<MovieEntity>> loadPopularMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieEntity> movies);

}
