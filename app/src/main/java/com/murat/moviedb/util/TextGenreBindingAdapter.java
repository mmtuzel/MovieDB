package com.murat.moviedb.util;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.murat.moviedb.data.model.MovieDetail;
import com.murat.moviedb.data.model.TvShowDetail;

import java.util.List;

public class TextGenreBindingAdapter {

    @BindingAdapter("app:movieGenres")
    public static void setMovieGenres(TextView textView, List<MovieDetail.Genre> genres) {
        if (genres != null) {
            for (int i = 0; i < genres.size(); i++) {
                textView.append(genres.get(i).getName());
                if (i + 1 != genres.size()) {
                    textView.append(", ");
                }
            }
        }
    }

    @BindingAdapter("app:tvShowGenres")
    public static void setTvShowGenres(TextView textView, List<TvShowDetail.Genre> genres) {
        if (genres != null) {
            for (int i = 0; i < genres.size(); i++) {
                textView.append(genres.get(i).getName());
                if (i + 1 != genres.size()) {
                    textView.append(", ");
                }
            }
        }
    }
}
