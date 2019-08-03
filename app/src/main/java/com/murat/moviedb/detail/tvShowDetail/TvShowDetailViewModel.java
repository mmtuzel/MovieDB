package com.murat.moviedb.detail.tvShowDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.murat.moviedb.data.model.Credits;
import com.murat.moviedb.data.model.TvShowDetail;

public class TvShowDetailViewModel extends ViewModel {
    private TvShowDetailRepository repository;
    private LiveData<TvShowDetail> tvShowDetail;
    private LiveData<Credits> tvShowCredits;

    public TvShowDetailViewModel(int tvShowId) {
        super();
        repository = new TvShowDetailRepository();
        tvShowDetail = repository.getTvShowDetail(tvShowId);
        tvShowCredits = repository.getTvShowCredits(tvShowId);
    }

    public LiveData<TvShowDetail> getTvShowDetail() {
        return tvShowDetail;
    }

    public LiveData<Credits> getTvShowCredits() {
        return tvShowCredits;
    }
}
