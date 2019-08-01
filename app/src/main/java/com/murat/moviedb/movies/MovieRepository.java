package com.murat.moviedb.movies;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.murat.moviedb.data.local.MovieDatabase;
import com.murat.moviedb.data.local.dao.MovieDao;
import com.murat.moviedb.data.model.MovieEntity;
import com.murat.moviedb.data.model.MovieResponse;
import com.murat.moviedb.data.remote.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static final String TAG = "MovieRepository";

    private MovieDao movieDao;
    private LiveData<List<MovieEntity>> topRatedMovies;
    private LiveData<List<MovieEntity>> nowPlayingMovies;
    private LiveData<List<MovieEntity>> popularMovies;

    public MovieRepository(Application application) {
        MovieDatabase database = MovieDatabase.getDatabase(application);
        movieDao = database.movieDao();
        topRatedMovies = movieDao.loadAllTopRatedMovies();
        nowPlayingMovies = movieDao.loadNowPlayingMovies();
        popularMovies = movieDao.loadPopularMovies();
    }

    public LiveData<List<MovieEntity>> getTopRatedMovies() {
        return topRatedMovies;
    }

    public LiveData<List<MovieEntity>> getNowPlayingMovies() {
        return nowPlayingMovies;
    }

    public LiveData<List<MovieEntity>> getPopularMovies() {
        return popularMovies;
    }

    public void insertTopRatedMovies() {
        Call<MovieResponse> call = ApiClient.getApiService().getTopRatedMovies();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse isSuccessful");
                    //topRatedMovieDao.insertAll(response.body().getMovies());
                    Log.d(TAG, "thread: " + Thread.currentThread().getName());
                    List<MovieEntity> movies = response.body().getMovies();
                    for (MovieEntity movie : movies) {
                        movie.setMovieType(0);
                        Log.d(TAG, "name: " + movie.getTitle());
                        Log.d(TAG, "vote_average: " + movie.getVoteAverage());
                    }
                    new InsertMoviesTask(movieDao).execute(movies);
                } else {
                    Log.d(TAG, "onResponse fail " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void insertNowPlayingMovies() {
        Call<MovieResponse> call = ApiClient.getApiService().getNowPlayingMovies();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse isSuccessful");
                    //topRatedMovieDao.insertAll(response.body().getMovies());
                    Log.d(TAG, "thread: " + Thread.currentThread().getName());
                    List<MovieEntity> movies = response.body().getMovies();
                    for (MovieEntity movie : movies) {
                        movie.setMovieType(1);
                        Log.d(TAG, "name: " + movie.getTitle());
                        Log.d(TAG, "vote_average: " + movie.getVoteAverage());
                    }
                    new InsertMoviesTask(movieDao).execute(movies);
                } else {
                    Log.d(TAG, "onResponse fail " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void insertPopularMovies() {
        Call<MovieResponse> call = ApiClient.getApiService().getPopularMovies();
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse isSuccessful");
                    //topRatedMovieDao.insertAll(response.body().getMovies());
                    Log.d(TAG, "thread: " + Thread.currentThread().getName());
                    List<MovieEntity> movies = response.body().getMovies();
                    for (MovieEntity movie : movies) {
                        movie.setMovieType(2);
                        Log.d(TAG, "name: " + movie.getTitle());
                        Log.d(TAG, "vote_average: " + movie.getVoteAverage());
                    }
                    new InsertMoviesTask(movieDao).execute(movies);
                } else {
                    Log.d(TAG, "onResponse fail " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private static class InsertMoviesTask extends AsyncTask<List<MovieEntity>, Void, Void> {
        private MovieDao dao;

        InsertMoviesTask(MovieDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List<MovieEntity>... lists) {
            dao.insertAll(lists[0]);
            return null;
        }
    }
}
