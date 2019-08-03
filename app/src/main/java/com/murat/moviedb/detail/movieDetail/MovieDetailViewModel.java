package com.murat.moviedb.detail.movieDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.murat.moviedb.data.model.Credits;
import com.murat.moviedb.data.model.MovieDetail;
import com.murat.moviedb.data.model.Trailer;

public class MovieDetailViewModel extends ViewModel {
    private MovieDetailRepository repository;
    private LiveData<MovieDetail> movieDetail;
    private LiveData<Credits> movieCredits;
    private LiveData<Trailer> trailer;

    public MovieDetailViewModel(int movieId) {
        super();
        repository = new MovieDetailRepository();
        movieDetail = repository.getMovieDetail(movieId);
        movieCredits = repository.getMovieCredits(movieId);
        trailer = repository.getTrailer(movieId);
    }

    public LiveData<MovieDetail> getMovieDetail() {
        return movieDetail;
    }

    public LiveData<Credits> getMovieCredits() {
        return movieCredits;
    }

    public LiveData<Trailer> getTrailer() {
        return trailer;
    }
}
