package com.murat.moviedb.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.murat.moviedb.data.model.TvShowEntity;

import java.util.List;

@Dao
public interface TvDao {

    @Query("SELECT * FROM tvShows WHERE tvShowType = 0")
    LiveData<List<TvShowEntity>> loadTopRatedTvSeries();

    @Query("SELECT * FROM tvShows WHERE tvShowType = 1")
    LiveData<List<TvShowEntity>> loadPopularTvSeries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TvShowEntity> tv);

}
