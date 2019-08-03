package com.murat.moviedb.movies;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.murat.moviedb.MainActivity;
import com.murat.moviedb.R;
import com.murat.moviedb.data.model.MovieEntity;
import com.murat.moviedb.databinding.FragmentMoviesBinding;
import com.murat.moviedb.util.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    public static final String TAG = "MoviesFragment";

    private FragmentMoviesBinding binding;

    private MoviesAdapter topRatedMoviesAdapter;
    private MoviesAdapter nowPlayingMoviesAdapter;
    private MoviesAdapter popularMoviesAdapter;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        topRatedMoviesAdapter = new MoviesAdapter(Constants.TYPE_TOP_RATED_MOVIE, movieClickCallback);
        binding.rvTopRated.setAdapter(topRatedMoviesAdapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvTopRated);

        nowPlayingMoviesAdapter = new MoviesAdapter(Constants.TYPE_NOW_PLAYING_MOVIE, movieClickCallback);
        binding.rvNowPlaying.setAdapter(nowPlayingMoviesAdapter);

        popularMoviesAdapter = new MoviesAdapter(Constants.TYPE_POPULAR_MOVIE, movieClickCallback);
        binding.rvPopular.setAdapter(popularMoviesAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movieViewModel.saveTopRatedMovies();
        subscribeTopRatedMovies(movieViewModel.getTopRatedMovies());

        movieViewModel.saveNowPLayingMovies();
        subscribeNowPlayingMovies(movieViewModel.getNowPlayingMovies());

        movieViewModel.savePopularMovies();
        subscribePopularMovies(movieViewModel.getPopularMovies());

    }

    private void subscribeTopRatedMovies(LiveData<List<MovieEntity>> liveData) {
        liveData.observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                Log.d(TAG, "topRatedMovies onChanged");
                Log.d(TAG, "movieEntities size: " + movieEntities.size());
                topRatedMoviesAdapter.setMovies(movieEntities);
                for (MovieEntity movie : movieEntities) {
                    //Log.d(TAG, "name: " + movie.getPosterPath());
                }
            }
        });
    }

    private void subscribeNowPlayingMovies(LiveData<List<MovieEntity>> liveData) {
        liveData.observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                Log.d(TAG, "nowPlayingMovies onChanged");
                Log.d(TAG, "movieEntities size: " + movieEntities.size());
                nowPlayingMoviesAdapter.setMovies(movieEntities);
                for (MovieEntity movie : movieEntities) {
                    //Log.d(TAG, "name: " + movie.getPosterPath());
                }
            }
        });
    }

    private void subscribePopularMovies(LiveData<List<MovieEntity>> liveData) {
        liveData.observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                Log.d(TAG, "popularMovies onChanged");
                Log.d(TAG, "movieEntities size: " + movieEntities.size());
                popularMoviesAdapter.setMovies(movieEntities);
                for (MovieEntity movie : movieEntities) {
                    //Log.d(TAG, "name: " + movie.getPosterPath());
                }
            }
        });
    }

    private MovieClickCallback movieClickCallback = new MovieClickCallback() {
        @Override
        public void onClick(MovieEntity movieEntity) {
            //Toast.makeText(getActivity(), movieEntity.getTitle(), Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).openMovieDetail(movieEntity.getId());
        }
    };
}
