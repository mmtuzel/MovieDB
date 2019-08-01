package com.murat.moviedb.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.murat.moviedb.R;

public class DetailActivity extends AppCompatActivity {
    // TODO: Use single activity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int detailId = getIntent().getIntExtra("movieId", 0);
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(detailId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, movieDetailFragment, MovieDetailFragment.TAG).commit();
    }
}
