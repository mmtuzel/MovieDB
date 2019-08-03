package com.murat.moviedb.tv;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.murat.moviedb.R;
import com.murat.moviedb.data.model.TvShowEntity;
import com.murat.moviedb.databinding.FragmentTvShowsBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowsFragment extends Fragment {
    public static final String TAG = "TvFragment";

    private FragmentTvShowsBinding binding;

    private TopRatedTvShowsAdapter topRatedTvSeriesAdapter;
    private PopularTvShowsAdapter popularTvSeriesAdapter;

    public TvShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_shows, container, false);

        topRatedTvSeriesAdapter = new TopRatedTvShowsAdapter();
        binding.rvTopRated.setAdapter(topRatedTvSeriesAdapter);

        popularTvSeriesAdapter = new PopularTvShowsAdapter();
        binding.rvPopular.setAdapter(popularTvSeriesAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TvViewModel tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);

        tvViewModel.saveTopRatedTvSeries();
        subscribeTopRatedTvSeries(tvViewModel.getTopRatedTvSeries());

        tvViewModel.savePopularTvSeries();
        subscribePopularTvSeries(tvViewModel.getPopularTvSeries());
    }

    private void subscribeTopRatedTvSeries(LiveData<List<TvShowEntity>> liveData) {
        liveData.observe(this, new Observer<List<TvShowEntity>>() {
            @Override
            public void onChanged(List<TvShowEntity> tvEntities) {
                topRatedTvSeriesAdapter.setTvSeries(tvEntities);
            }
        });
    }

    private void subscribePopularTvSeries(LiveData<List<TvShowEntity>> liveData) {
        liveData.observe(this, new Observer<List<TvShowEntity>>() {
            @Override
            public void onChanged(List<TvShowEntity> tvEntities) {
                popularTvSeriesAdapter.setTvSeries(tvEntities);
            }
        });
    }
}
