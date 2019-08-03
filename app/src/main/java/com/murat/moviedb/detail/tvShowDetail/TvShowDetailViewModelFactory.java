package com.murat.moviedb.detail.tvShowDetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TvShowDetailViewModelFactory implements ViewModelProvider.Factory {
    private int movieId;

    public TvShowDetailViewModelFactory(int movieId) {
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return modelClass.cast(new TvShowDetailViewModel(movieId));
    }
}
