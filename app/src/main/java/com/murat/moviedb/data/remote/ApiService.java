package com.murat.moviedb.data.remote;

import com.murat.moviedb.data.model.MovieCredit;
import com.murat.moviedb.data.model.MovieDetail;
import com.murat.moviedb.data.model.MoviesResponse;
import com.murat.moviedb.data.model.TvShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    // Movies
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies();

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies();

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies();

    // TV
    @GET("tv/top_rated")
    Call<TvShowsResponse> getTopRatedTvSeries();

    @GET("tv/popular")
    Call<TvShowsResponse> getPopularTvSeries();

    // Details
    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}/casts")
    Call<MovieCredit> getMovieCredit(@Path("movie_id") int movieId);
}
