package com.murat.moviedb.tv;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.murat.moviedb.data.model.TvEntity;

import java.util.List;

public class TvViewModel extends AndroidViewModel {
    private static final String TAG = "TvViewModel";

    private TvRepository repository;
    private LiveData<List<TvEntity>> topRatedTvSeries;
    private LiveData<List<TvEntity>> popularTvSeries;

    public TvViewModel(@NonNull Application application) {
        super(application);
        repository = new TvRepository(application);
        topRatedTvSeries = repository.getTopRatedTvSeries();
        popularTvSeries = repository.getPopularTvSeries();
    }

    public LiveData<List<TvEntity>> getTopRatedTvSeries() {
        return topRatedTvSeries;
    }

    public void saveTopRatedTvSeries() {
        repository.insertTopRatedTvSeries();
    }

    public LiveData<List<TvEntity>> getPopularTvSeries() {
        return popularTvSeries;
    }

    public void savePopularTvSeries() {
        repository.insertPopularTvSeries();
    }
}
