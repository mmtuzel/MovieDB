package com.murat.moviedb.movies;

import com.murat.moviedb.data.model.MovieEntity;

public interface MovieClickCallback {
    void onClick(MovieEntity movieEntity);
}
