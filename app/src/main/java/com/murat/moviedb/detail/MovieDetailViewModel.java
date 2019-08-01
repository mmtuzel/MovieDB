package com.murat.moviedb.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.murat.moviedb.data.model.MovieCredit;
import com.murat.moviedb.data.model.MovieDetail;

public class MovieDetailViewModel extends ViewModel {
    private DetailRepository repository;
    private LiveData<MovieDetail> movieDetail;
    private LiveData<MovieCredit> movieCredit;

    public MovieDetailViewModel(int movieId) {
        super();
        repository = new DetailRepository();
        movieDetail = repository.getMovieDetail(movieId);
        movieCredit = repository.getMovieCredit(movieId);
    }

    public LiveData<MovieDetail> getMovieDetail() {
        return movieDetail;
    }

    public LiveData<MovieCredit> getMovieCredit() {
        return movieCredit;
    }
}
