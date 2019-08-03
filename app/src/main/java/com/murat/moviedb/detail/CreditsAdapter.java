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
    private List<Credits.Cast> casts;

    public void setCasts(List<Credits.Cast> casts) {
        this.casts = casts;
        notifyDataSetChanged();
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
        holder.binding.setProfilePath(casts.get(position).getProfilePath());
        holder.binding.setName(casts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return casts == null ? 0 : casts.size();
    }

    class CreditViewHolder extends RecyclerView.ViewHolder {
        ItemMovieCreditBinding binding;

        public CreditViewHolder(@NonNull ItemMovieCreditBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
