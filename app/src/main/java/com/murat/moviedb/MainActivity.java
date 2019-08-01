package com.murat.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.murat.moviedb.detail.DetailActivity;
import com.murat.moviedb.movies.MoviesFragment;
import com.murat.moviedb.tv.TvFragment;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesFragment moviesFragment = new MoviesFragment();
        //TvFragment tvFragment = new TvFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, moviesFragment, MoviesFragment.TAG).commit();
    }

    public void openMovieDetail(int movieId) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("movieId", movieId);
        startActivity(detailIntent);
    }
}
