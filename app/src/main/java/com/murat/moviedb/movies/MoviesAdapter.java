package com.murat.moviedb.movies;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.murat.moviedb.R;
import com.murat.moviedb.data.model.MovieEntity;
import com.murat.moviedb.databinding.ItemNowPlayingMovieBinding;
import com.murat.moviedb.databinding.ItemPopularMovieBinding;
import com.murat.moviedb.databinding.ItemTopRatedMovieBinding;
import com.murat.moviedb.util.Constants;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int movieType;

    private List<MovieEntity> movies;
    private MovieClickCallback clickCallback;

    public MoviesAdapter(int movieType, MovieClickCallback clickCallback) {
        this.movieType = movieType;
        this.clickCallback = clickCallback;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (movieType == Constants.TYPE_TOP_RATED_MOVIE) {
            return Constants.TYPE_TOP_RATED_MOVIE;
        } else if (movieType == Constants.TYPE_NOW_PLAYING_MOVIE) {
            return Constants.TYPE_NOW_PLAYING_MOVIE;
        } else if (movieType == Constants.TYPE_POPULAR_MOVIE) {
            return Constants.TYPE_POPULAR_MOVIE;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_TOP_RATED_MOVIE) {
            ItemTopRatedMovieBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_top_rated_movie, parent, false
            );
            binding.setCallback(clickCallback);
            return new TopRatedMovieViewHolder(binding);
        } else if (viewType == Constants.TYPE_NOW_PLAYING_MOVIE) {
            ItemNowPlayingMovieBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_now_playing_movie, parent, false
            );
            binding.setCallback(clickCallback);
            return new NowPlayingMovieHolder(binding);
        } else {
            ItemPopularMovieBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_popular_movie, parent, false
            );
            binding.setCallback(clickCallback);
            return new PopularMovieViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopRatedMovieViewHolder) {
            ((TopRatedMovieViewHolder) holder).binding.setMovie(movies.get(position));
            ((TopRatedMovieViewHolder) holder).binding.executePendingBindings();
        } else if (holder instanceof NowPlayingMovieHolder) {
            ((NowPlayingMovieHolder) holder).binding.setMovie(movies.get(position));
            ((NowPlayingMovieHolder) holder).binding.executePendingBindings();
        } else if (holder instanceof PopularMovieViewHolder) {
            ((PopularMovieViewHolder) holder).binding.setMovie(movies.get(position));
            ((PopularMovieViewHolder) holder).binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    class TopRatedMovieViewHolder extends RecyclerView.ViewHolder {
        public ItemTopRatedMovieBinding binding;

        public TopRatedMovieViewHolder(@NonNull ItemTopRatedMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class NowPlayingMovieHolder extends RecyclerView.ViewHolder {
        public ItemNowPlayingMovieBinding binding;

        public NowPlayingMovieHolder(@NonNull ItemNowPlayingMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class PopularMovieViewHolder extends RecyclerView.ViewHolder {
        public ItemPopularMovieBinding binding;

        public PopularMovieViewHolder(@NonNull ItemPopularMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
