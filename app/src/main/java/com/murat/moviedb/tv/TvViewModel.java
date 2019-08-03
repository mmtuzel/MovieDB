package com.murat.moviedb.tv;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.murat.moviedb.data.model.TvShowEntity;

import java.util.List;

public class TvViewModel extends AndroidViewModel {
    private static final String TAG = "TvViewModel";

    private TvShowRepository repository;
    private LiveData<List<TvShowEntity>> topRatedTvSeries;
    private LiveData<List<TvShowEntity>> popularTvSeries;

    public TvViewModel(@NonNull Application application) {
        super(application);
        repository = new TvShowRepository(application);
        topRatedTvSeries = repository.getTopRatedTvSeries();
        popularTvSeries = repository.getPopularTvSeries();
    }

    public LiveData<List<TvShowEntity>> getTopRatedTvSeries() {
        return topRatedTvSeries;
    }

    public void saveTopRatedTvSeries() {
        repository.insertTopRatedTvSeries();
    }

    public LiveData<List<TvShowEntity>> getPopularTvSeries() {
        return popularTvSeries;
    }

    public void savePopularTvSeries() {
        repository.insertPopularTvSeries();
    }
}
