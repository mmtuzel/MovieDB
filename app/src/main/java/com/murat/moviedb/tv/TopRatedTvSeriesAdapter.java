package com.murat.moviedb.tv;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.murat.moviedb.R;
import com.murat.moviedb.data.model.TvEntity;
import com.murat.moviedb.databinding.ItemTopRatedTvBinding;

import java.util.List;

public class TopRatedTvSeriesAdapter extends RecyclerView.Adapter<TopRatedTvSeriesAdapter.MovieViewHolder>{
    private List<TvEntity> tvShow;

    public TopRatedTvSeriesAdapter() {
    }

    public void setTvSeries(List<TvEntity> tvShow) {
        this.tvShow = tvShow;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTopRatedTvBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_top_rated_tv, parent, false
        );
        //binding.setCallback(clickCallback);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.binding.setTvShow(tvShow.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return tvShow == null ? 0 : tvShow.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ItemTopRatedTvBinding binding;

        public MovieViewHolder(@NonNull ItemTopRatedTvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}