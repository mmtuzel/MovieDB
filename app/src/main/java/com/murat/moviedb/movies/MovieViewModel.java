package com.murat.moviedb.movies;

import android.app.Application;

import com.murat.moviedb.data.model.MovieEntity;
import com.murat.moviedb.movies.MovieRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MovieViewModel extends AndroidViewModel {
    private static final String TAG = "MovieViewModel";

    private MovieRepository repository;
    private LiveData<List<MovieEntity>> topRatedMovies;
    private LiveData<List<MovieEntity>> nowPlayingMovies;
    private LiveData<List<MovieEntity>> popularMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        topRatedMovies = repository.getTopRatedMovies();
        nowPlayingMovies = repository.getNowPlayingMovies();
        popularMovies = repository.getPopularMovies();
    }

    public LiveData<List<MovieEntity>> getTopRatedMovies() {
        return topRatedMovies;
    }

    public void saveTopRatedMovies() {
        repository.insertTopRatedMovies();
    }

    public LiveData<List<MovieEntity>> getNowPlayingMovies() {
        return nowPlayingMovies;
    }

    public void saveNowPLayingMovies() {
        repository.insertNowPlayingMovies();
    }

    public LiveData<List<MovieEntity>> getPopularMovies() {
        return popularMovies;
    }

    public void savePopularMovies() {
        repository.insertPopularMovies();
    }
}
