package com.murat.moviedb.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.murat.moviedb.data.model.TvEntity;

import java.util.List;

@Dao
public interface TvDao {

    @Query("SELECT * FROM tv WHERE tvType = 0")
    LiveData<List<TvEntity>> loadTopRatedTvSeries();

    @Query("SELECT * FROM tv WHERE tvType = 1")
    LiveData<List<TvEntity>> loadPopularTvSeries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TvEntity> tv);

}
