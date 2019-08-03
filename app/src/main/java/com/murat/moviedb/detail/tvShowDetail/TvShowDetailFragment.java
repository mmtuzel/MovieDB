package com.murat.moviedb.detail.tvShowDetail;


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
import com.murat.moviedb.data.model.Credits;
import com.murat.moviedb.data.model.TvShowDetail;
import com.murat.moviedb.databinding.FragmentTvShowDetailBinding;
import com.murat.moviedb.detail.CreditsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowDetailFragment extends Fragment {
    private static final String TAG = "TvShowDetailFragment";

    private static final String ARG_TV_SHOW_ID = "tvShowId";

    private FragmentTvShowDetailBinding binding;
    private CreditsAdapter creditsAdapter;

    private int tvShowId;

    public static TvShowDetailFragment newInstance(int tvShowId) {

        Bundle args = new Bundle();
        args.putInt(ARG_TV_SHOW_ID, tvShowId);

        TvShowDetailFragment fragment = new TvShowDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TvShowDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_TV_SHOW_ID)) {
            tvShowId = getArguments().getInt(ARG_TV_SHOW_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv_show_detail, container, false);

        creditsAdapter = new CreditsAdapter();
        binding.rvTvShowCredits.setAdapter(creditsAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TvShowDetailViewModel tvShowDetailViewModel = ViewModelProviders.of(
                this,
                new TvShowDetailViewModelFactory(tvShowId)
        ).get(TvShowDetailViewModel.class);

        subscribeTvShowDetail(tvShowDetailViewModel.getTvShowDetail());
        subscribeTvShowCredits(tvShowDetailViewModel.getTvShowCredits());
    }

    private void subscribeTvShowDetail(LiveData<TvShowDetail> liveData) {
        liveData.observe(this, new Observer<TvShowDetail>() {
            @Override
            public void onChanged(TvShowDetail tvShowDetail) {
                binding.setTvShowDetail(tvShowDetail);
            }
        });
    }

    private void subscribeTvShowCredits(LiveData<Credits> liveData) {
        liveData.observe(this, new Observer<Credits>() {
            @Override
            public void onChanged(Credits credits) {
                creditsAdapter.setCasts(credits.getCast(), credits.getCrew().get(0));
            }
        });
    }
}
