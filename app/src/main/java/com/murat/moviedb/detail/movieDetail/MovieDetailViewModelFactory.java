package com.murat.moviedb.detail.movieDetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetailViewModelFactory implements ViewModelProvider.Factory {
    private int movieId;

    public MovieDetailViewModelFactory(int movieId) {
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //return (T) new MovieDetailViewModel(movieId);
        return modelClass.cast(new MovieDetailViewModel(movieId));
    }
}
