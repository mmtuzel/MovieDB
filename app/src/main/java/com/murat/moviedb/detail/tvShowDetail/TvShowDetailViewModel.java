package com.murat.moviedb.detail.tvShowDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.murat.moviedb.data.model.Credits;
import com.murat.moviedb.data.model.Trailer;
import com.murat.moviedb.data.model.TvShowDetail;

public class TvShowDetailViewModel extends ViewModel {
    private TvShowDetailRepository repository;
    private LiveData<TvShowDetail> tvShowDetail;
    private LiveData<Credits> tvShowCredits;
    private LiveData<Trailer> trailer;

    public TvShowDetailViewModel(int tvShowId) {
        super();
        repository = new TvShowDetailRepository();
        tvShowDetail = repository.getTvShowDetail(tvShowId);
        tvShowCredits = repository.getTvShowCredits(tvShowId);
        trailer = repository.getTrailer(tvShowId);
    }

    public LiveData<TvShowDetail> getTvShowDetail() {
        return tvShowDetail;
    }

    public LiveData<Credits> getTvShowCredits() {
        return tvShowCredits;
    }

    public LiveData<Trailer> getTrailer() {
        return trailer;
    }
}
