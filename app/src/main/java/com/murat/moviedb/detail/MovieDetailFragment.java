package com.murat.moviedb.detail;


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
import com.murat.moviedb.data.model.MovieCredit;
import com.murat.moviedb.data.model.MovieDetail;
import com.murat.moviedb.databinding.FragmentMovieDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {
    public static final String TAG = "MovieDetailFragment";

    private static final String ARG_MOVIE_ID = "movieId";

    private FragmentMovieDetailBinding binding;
    private CreditAdapter creditAdapter;

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

        creditAdapter = new CreditAdapter();
        binding.rvMovieCredit.setAdapter(creditAdapter);

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
        subscribeMovieCredit(movieDetailViewModel.getMovieCredit());
    }

    private void subscribeMovieDetail(LiveData<MovieDetail> liveData) {
        liveData.observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(MovieDetail movieDetail) {
                binding.setMovieDetail(movieDetail);
            }
        });
    }

    private void subscribeMovieCredit(LiveData<MovieCredit> liveData) {
        liveData.observe(this, new Observer<MovieCredit>() {
            @Override
            public void onChanged(MovieCredit movieCredit) {
                creditAdapter.setCasts(movieCredit.getCast());
            }
        });
    }
}