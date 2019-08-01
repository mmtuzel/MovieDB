package com.murat.moviedb.tv;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.murat.moviedb.data.local.MovieDatabase;
import com.murat.moviedb.data.local.dao.TvDao;
import com.murat.moviedb.data.model.TvEntity;
import com.murat.moviedb.data.model.TvResponse;
import com.murat.moviedb.data.remote.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvRepository {
    private static final String TAG = "TvRepository";

    private TvDao tvDao;
    private LiveData<List<TvEntity>> topRatedTvSeries;
    private LiveData<List<TvEntity>> popularTvSeries;

    public TvRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getDatabase(application);
        tvDao = movieDatabase.tvDao();
        topRatedTvSeries = tvDao.loadTopRatedTvSeries();
        popularTvSeries = tvDao.loadPopularTvSeries();
    }

    public LiveData<List<TvEntity>> getTopRatedTvSeries() {
        return topRatedTvSeries;
    }

    public LiveData<List<TvEntity>> getPopularTvSeries() {
        return popularTvSeries;
    }

    public void insertTopRatedTvSeries() {
        Call<TvResponse> call = ApiClient.getApiService().getTopRatedTvSeries();
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse successful");
                    List<TvEntity> tvSeries = response.body().getResults();
                    for (TvEntity tvShow : tvSeries) {
                        tvShow.setTvType(0);
                        Log.d(TAG, "name: " + tvShow.getName());
                    }
                    new InsertTvSeriesTask(tvDao).execute(tvSeries);
                } else {
                    Log.d(TAG, "onResponse fail " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void insertPopularTvSeries() {
        Call<TvResponse> call = ApiClient.getApiService().getPopularTvSeries();
        call.enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse successful");
                    List<TvEntity> tvSeries = response.body().getResults();
                    for (TvEntity tvShow : tvSeries) {
                        tvShow.setTvType(1);
                        Log.d(TAG, "name: " + tvShow.getName());
                    }
                    new InsertTvSeriesTask(tvDao).execute(tvSeries);
                } else {
                    Log.d(TAG, "onResponse fail " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private static class InsertTvSeriesTask extends AsyncTask<List<TvEntity>, Void, Void> {
        private TvDao dao;

        InsertTvSeriesTask(TvDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List<TvEntity>... lists) {
            dao.insertAll(lists[0]);
            return null;
        }
    }
}
