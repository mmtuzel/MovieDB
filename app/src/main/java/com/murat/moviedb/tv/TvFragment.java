package com.murat.moviedb.tv;


import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.murat.moviedb.data.model.TvEntity;
import com.murat.moviedb.databinding.FragmentTvBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    private static final String TAG = "TvFragment";

    private FragmentTvBinding binding;

    private TopRatedTvSeriesAdapter topRatedTvSeriesAdapter;
    private PopularTvSeriesAdapter popularTvSeriesAdapter;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv, container, false);

        topRatedTvSeriesAdapter = new TopRatedTvSeriesAdapter();
        binding.rvTopRated.setAdapter(topRatedTvSeriesAdapter);

        popularTvSeriesAdapter = new PopularTvSeriesAdapter();
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

    private void subscribeTopRatedTvSeries(LiveData<List<TvEntity>> liveData) {
        liveData.observe(this, new Observer<List<TvEntity>>() {
            @Override
            public void onChanged(List<TvEntity> tvEntities) {
                topRatedTvSeriesAdapter.setTvSeries(tvEntities);
            }
        });
    }

    private void subscribePopularTvSeries(LiveData<List<TvEntity>> liveData) {
        liveData.observe(this, new Observer<List<TvEntity>>() {
            @Override
            public void onChanged(List<TvEntity> tvEntities) {
                popularTvSeriesAdapter.setTvSeries(tvEntities);
            }
        });
    }
}
