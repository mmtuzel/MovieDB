package com.murat.moviedb.data.remote;

import com.murat.moviedb.data.model.MovieCredit;
import com.murat.moviedb.data.model.MovieDetail;
import com.murat.moviedb.data.model.MovieResponse;
import com.murat.moviedb.data.model.TvResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    // Movies
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies();

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies();

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies();

    // TV
    @GET("tv/top_rated")
    Call<TvResponse> getTopRatedTvSeries();

    @GET("tv/popular")
    Call<TvResponse> getPopularTvSeries();

    // Details
    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}/casts")
    Call<MovieCredit> getMovieCredit(@Path("movie_id") int movieId);
}
