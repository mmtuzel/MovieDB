package com.murat.moviedb.detail.tvShowDetail;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;

import com.murat.moviedb.data.model.Credits;
import com.murat.moviedb.data.model.Trailer;
import com.murat.moviedb.data.model.TvShowDetail;
import com.murat.moviedb.data.remote.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailRepository {
    private static final String TAG = "TvShowDetailRepository";

    private MediatorLiveData<TvShowDetail> tvShowDetail;
    private MediatorLiveData<Credits> tvShowCredits;
    private MediatorLiveData<Trailer> trailer;

    public TvShowDetailRepository() {
        tvShowDetail = new MediatorLiveData<>();
        tvShowCredits = new MediatorLiveData<>();
        trailer = new MediatorLiveData<>();
    }

    private void requestTvShowDetail(int tvShowId) {
        Call<TvShowDetail> call = ApiClient.getApiService().getTvShowDetail(tvShowId);
        call.enqueue(new Callback<TvShowDetail>() {
            @Override
            public void onResponse(Call<TvShowDetail> call, Response<TvShowDetail> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "tvShowDetail onResponse successful");
                    tvShowDetail.setValue(response.body());
                } else {
                    Log.d(TAG, "tvShowDetail onResponse fail: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TvShowDetail> call, Throwable t) {
                Log.d(TAG, "tvShowDetail onFailure: " + t.getMessage());
            }
        });
    }

    public MediatorLiveData<TvShowDetail> getTvShowDetail(int tvShowId) {
        requestTvShowDetail(tvShowId);
        return tvShowDetail;
    }

    private void requestTvShowCredits(int tvShowId) {
        Call<Credits> call = ApiClient.getApiService().getTvShowCredits(tvShowId);
        call.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "tvShowCredits onResponse successful");
                    tvShowCredits.setValue(response.body());
                } else {
                    try {
                        Log.d(TAG, "tvShowCredits onResponse fail: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                Log.d(TAG, "tvShowCredits onFailure: " + t.getMessage());
            }
        });
    }

    public MediatorLiveData<Credits> getTvShowCredits(int tvShowId) {
        requestTvShowCredits(tvShowId);
        return tvShowCredits;
    }

    private void requestTrailer(int tvShowId) {
        Call<Trailer> call = ApiClient.getApiService().getMovieTrailers(tvShowId);
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

    public MediatorLiveData<Trailer> getTrailer(int tvShowId) {
        requestTrailer(tvShowId);
        return trailer;
    }
}
