package com.murat.moviedb.detail.tvShowDetail;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.murat.moviedb.R;
import com.murat.moviedb.data.model.Credits;
import com.murat.moviedb.data.model.Trailer;
import com.murat.moviedb.data.model.TvShowDetail;
import com.murat.moviedb.databinding.FragmentTvShowDetailBinding;
import com.murat.moviedb.detail.CreditsAdapter;
import com.murat.moviedb.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowDetailFragment extends Fragment {
    private static final String TAG = "TvShowDetailFragment";

    private static final String ARG_TV_SHOW_ID = "tvShowId";

    private FragmentTvShowDetailBinding binding;
    private CreditsAdapter creditsAdapter;

    private YouTubePlayer.OnInitializedListener youtubeInitializedListener;
    private YouTubePlayerSupportFragment playerFragment;

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

        // youtube api doesn't support androidX library. warning is negligible
        // https://stackoverflow.com/a/52695996
        playerFragment = new YouTubePlayerSupportFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_youtube, playerFragment).commit();
        //binding.fragmentYoutube.initialize(Constants.API_KEY, youtubeInitializedListener);
        binding.setIsVideoStarted(false);
        binding.ivPlay.setOnClickListener(v -> {
            binding.setIsVideoStarted(true);
            playerFragment.initialize(Constants.API_KEY, youtubeInitializedListener);
        });

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
        subscribeMovieTrailer(tvShowDetailViewModel.getTrailer());
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

    private void subscribeMovieTrailer(LiveData<Trailer> liveData) {
        Log.d(TAG, "subscribeMovieTrailer");
        liveData.observe(this, new Observer<Trailer>() {
            @Override
            public void onChanged(Trailer trailer) {
                youtubeInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        Log.d(TAG, "onInitializationSuccess");
                        //youTubePlayer.setShowFullscreenButton(false);
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                        List<String> playList = new ArrayList<>();
                        List<Trailer.Result> results = trailer.getResults();
                        for (Trailer.Result result : results) {
                            Log.d(TAG, "result: " + result.getSite());
                            if (result.getSite().equals("YouTube")) {
                                Log.d(TAG, "key: " + result.getKey());
                                playList.add(result.getKey());
                            }
                        }
                        youTubePlayer.loadVideos(playList);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d(TAG, "onInitializationFailure: " + youTubeInitializationResult.name());
                    }
                };
            }
        });
    }
}
