package com.murat.moviedb.movies.topRated;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.murat.moviedb.R;
import com.murat.moviedb.data.model.MovieEntity;
import com.murat.moviedb.databinding.ItemTopRatedMovieBinding;
import com.murat.moviedb.movies.MovieClickCallback;

import java.util.List;

public class TopRatedMoviesAdapter extends RecyclerView.Adapter<TopRatedMoviesAdapter.MovieViewHolder>{
    private List<MovieEntity> movies;
    private MovieClickCallback clickCallback;

    public TopRatedMoviesAdapter(MovieClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTopRatedMovieBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_top_rated_movie, parent, false
        );
        binding.setCallback(clickCallback);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.binding.setMovie(movies.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ItemTopRatedMovieBinding binding;

        public MovieViewHolder(@NonNull ItemTopRatedMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
