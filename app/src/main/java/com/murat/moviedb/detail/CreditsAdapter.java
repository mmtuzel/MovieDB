package com.murat.moviedb.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.murat.moviedb.R;
import com.murat.moviedb.data.model.Credits;
import com.murat.moviedb.databinding.ItemMovieCreditBinding;

import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CreditViewHolder> {
    private static final int TYPE_DIRECTOR = 0;
    private static final int TYPE_CAST = 1;

    private List<Credits.Cast> casts;
    private Credits.Crew director;

    public void setCasts(List<Credits.Cast> casts) {
        this.casts = casts;
        notifyDataSetChanged();
    }

    public void setCasts(List<Credits.Cast> casts, Credits.Crew director) {
        this.casts = casts;
        this.director = director;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (director != null) {
            if (position == 0) {
                return TYPE_DIRECTOR;
            } else {
                return TYPE_CAST;
            }
        } else {
            return TYPE_CAST;
        }
        //return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieCreditBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_movie_credit, parent, false
        );
        return new CreditViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditViewHolder holder, int position) {
        if (director != null) {
            if (position == 0) {
                holder.binding.setProfilePath(director.getProfilePath());
                holder.binding.setName(director.getName());
                holder.binding.setIsDirector(true);
            } else {
                holder.binding.setProfilePath(casts.get(position - 1).getProfilePath());
                holder.binding.setName(casts.get(position - 1).getName());
                holder.binding.setIsDirector(false);
            }
        } else {
            holder.binding.setProfilePath(casts.get(position).getProfilePath());
            holder.binding.setName(casts.get(position).getName());
            holder.binding.setIsDirector(false);
        }
    }

    @Override
    public int getItemCount() {
        if (director != null) {
            return casts == null ? 0 : casts.size() + 1;
        } else {
            return casts == null ? 0 : casts.size();
        }
    }

    class CreditViewHolder extends RecyclerView.ViewHolder {
        ItemMovieCreditBinding binding;

        public CreditViewHolder(@NonNull ItemMovieCreditBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
