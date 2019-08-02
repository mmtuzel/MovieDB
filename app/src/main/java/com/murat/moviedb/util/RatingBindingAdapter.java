package com.murat.moviedb.util;

import android.widget.RatingBar;

import androidx.databinding.BindingAdapter;

public class RatingBindingAdapter {

    @BindingAdapter("app:setRatingValue")
    public static void setRating(RatingBar ratingBar, double rating) {
        float ratingValue = ((float) rating) / 2f;
        ratingBar.setRating(ratingValue);
    }
}
