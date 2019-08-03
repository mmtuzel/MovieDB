package com.murat.moviedb.tv;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.murat.moviedb.data.local.MovieDatabase;
import com.murat.moviedb.data.local.dao.TvDao;
import com.murat.moviedb.data.model.TvShowEntity;
import com.murat.moviedb.data.model.TvShowsResponse;
import com.murat.moviedb.data.remote.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowRepository {
    private static final String TAG = "TvRepository";

    private TvDao tvDao;
    private LiveData<List<TvShowEntity>> topRatedTvSeries;
    private LiveData<List<TvShowEntity>> popularTvSeries;

    public TvShowRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getDatabase(application);
        tvDao = movieDatabase.tvDao();
        topRatedTvSeries = tvDao.loadTopRatedTvSeries();
        popularTvSeries = tvDao.loadPopularTvSeries();
    }

    public LiveData<List<TvShowEntity>> getTopRatedTvSeries() {
        return topRatedTvSeries;
    }

    public LiveData<List<TvShowEntity>> getPopularTvSeries() {
        return popularTvSeries;
    }

    public void insertTopRatedTvSeries() {
        Call<TvShowsResponse> call = ApiClient.getApiService().getTopRatedTvSeries();
        call.enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse successful");
                    List<TvShowEntity> tvSeries = response.body().getResults();
                    for (TvShowEntity tvShow : tvSeries) {
                        tvShow.setTvShowType(0);
                        Log.d(TAG, "name: " + tvShow.getName());
                    }
                    new InsertTvSeriesTask(tvDao).execute(tvSeries);
                } else {
                    Log.d(TAG, "onResponse fail " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TvShowsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void insertPopularTvSeries() {
        Call<TvShowsResponse> call = ApiClient.getApiService().getPopularTvSeries();
        call.enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse successful");
                    List<TvShowEntity> tvSeries = response.body().getResults();
                    for (TvShowEntity tvShow : tvSeries) {
                        tvShow.setTvShowType(1);
                        Log.d(TAG, "name: " + tvShow.getName());
                    }
                    new InsertTvSeriesTask(tvDao).execute(tvSeries);
                } else {
                    Log.d(TAG, "onResponse fail " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TvShowsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private static class InsertTvSeriesTask extends AsyncTask<List<TvShowEntity>, Void, Void> {
        private TvDao dao;

        InsertTvSeriesTask(TvDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List<TvShowEntity>... lists) {
            dao.insertAll(lists[0]);
            return null;
        }
    }
}
