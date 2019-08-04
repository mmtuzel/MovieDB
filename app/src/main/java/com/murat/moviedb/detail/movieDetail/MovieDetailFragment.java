package com.murat.moviedb.detail.movieDetail;


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
import com.murat.moviedb.data.model.MovieDetail;
import com.murat.moviedb.data.model.Trailer;
import com.murat.moviedb.databinding.FragmentMovieDetailBinding;
import com.murat.moviedb.detail.CreditsAdapter;
import com.murat.moviedb.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {
    public static final String TAG = "MovieDetailFragment";

    private static final String ARG_MOVIE_ID = "movieId";

    private FragmentMovieDetailBinding binding;
    private CreditsAdapter creditsAdapter;

    private YouTubePlayer.OnInitializedListener youtubeInitializedListener;
    private YouTubePlayerSupportFragment playerFragment;

    private int movieId;

    public static MovieDetailFragment newInstance(int movieId) {

        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_MOVIE_ID)) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);

        creditsAdapter = new CreditsAdapter();
        binding.rvMovieCredits.setAdapter(creditsAdapter);

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

        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(
                this,
                new MovieDetailViewModelFactory(movieId)
        ).get(MovieDetailViewModel.class);

        subscribeMovieDetail(movieDetailViewModel.getMovieDetail());
        subscribeMovieCredits(movieDetailViewModel.getMovieCredits());
        subscribeMovieTrailer(movieDetailViewModel.getTrailer());
    }

    private void subscribeMovieDetail(LiveData<MovieDetail> liveData) {
        liveData.observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(MovieDetail movieDetail) {
                binding.setMovieDetail(movieDetail);
            }
        });
    }

    private void subscribeMovieCredits(LiveData<Credits> liveData) {
        liveData.observe(this, new Observer<Credits>() {
            @Override
            public void onChanged(Credits credits) {
                if (credits.getCrew() != null && credits.getCrew().size() > 0) {
                    creditsAdapter.setCasts(credits.getCast(), credits.getCrew().get(0));
                } else {
                    creditsAdapter.setCasts(credits.getCast());
                }
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
