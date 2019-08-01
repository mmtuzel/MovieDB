package com.murat.moviedb.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.murat.moviedb.data.model.MovieCredit;
import com.murat.moviedb.data.model.MovieDetail;
import com.murat.moviedb.data.remote.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRepository {
    private static final String TAG = "DetailRepository";

    private MediatorLiveData<MovieDetail> movieDetail;
    //private MediatorLiveData<MovieDetail> tvShowDetail;
    private MediatorLiveData<MovieCredit> movieCredit;

    public DetailRepository() {
        movieDetail = new MediatorLiveData<>();
        //tvShowDetail = new MediatorLiveData<>();
        movieCredit = new MediatorLiveData<>();
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

    private void requestMovieCredit(int movieId) {
        Call<MovieCredit> call = ApiClient.getApiService().getMovieCredit(movieId);
        call.enqueue(new Callback<MovieCredit>() {
            @Override
            public void onResponse(Call<MovieCredit> call, Response<MovieCredit> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse successful");
                    movieCredit.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse fail: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieCredit> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public MediatorLiveData<MovieCredit> getMovieCredit(int movieId) {
        requestMovieCredit(movieId);
        return movieCredit;
    }
}
