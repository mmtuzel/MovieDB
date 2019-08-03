package com.murat.moviedb.detail.movieDetail;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;

import com.murat.moviedb.data.model.Credits;
import com.murat.moviedb.data.model.MovieDetail;
import com.murat.moviedb.data.model.Trailer;
import com.murat.moviedb.data.remote.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailRepository {
    private static final String TAG = "MovieDetailRepository";

    private MediatorLiveData<MovieDetail> movieDetail;
    private MediatorLiveData<Credits> movieCredit;
    private MediatorLiveData<Trailer> trailer;

    public MovieDetailRepository() {
        movieDetail = new MediatorLiveData<>();
        movieCredit = new MediatorLiveData<>();
        trailer = new MediatorLiveData<>();
    }

    private void requestMovieDetail(int movieId) {
        Call<MovieDetail> call = ApiClient.getApiService().getMovieDetail(movieId);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse successful");
                    movieDetail.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse fail: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public MediatorLiveData<MovieDetail> getMovieDetail(int movieId) {
        requestMovieDetail(movieId);
        return movieDetail;
    }

    private void requestMovieCredits(int movieId) {
        Call<Credits> call = ApiClient.getApiService().getMovieCredits(movieId);
        call.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse successful");
                    movieCredit.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse fail: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public MediatorLiveData<Credits> getMovieCredits(int movieId) {
        requestMovieCredits(movieId);
        return movieCredit;
    }

    private void requestTrailer(int movieId) {
        Call<Trailer> call = ApiClient.getApiService().getMovieTrailers(movieId);
        call.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse successful");
                    trailer.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse fail: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public MediatorLiveData<Trailer> getTrailer(int movieId) {
        requestTrailer(movieId);
        return trailer;
    }
}
