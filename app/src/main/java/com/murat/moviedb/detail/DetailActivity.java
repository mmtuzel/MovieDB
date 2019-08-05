package com.murat.moviedb.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.murat.moviedb.R;
import com.murat.moviedb.detail.movieDetail.MovieDetailFragment;
import com.murat.moviedb.detail.tvShowDetail.TvShowDetailFragment;
import com.murat.moviedb.util.Constants;

public class DetailActivity extends AppCompatActivity {
    // TODO: Use single activity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setLayoutBehindStatusBar();

        String detailType = getIntent().getStringExtra(Constants.DETAIL_TYPE);
        if (detailType.equals(Constants.MOVIE_DETAIL_TYPE)) {
            int detailId = getIntent().getIntExtra(Constants.MOVIE_ID, 0);
            MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(detailId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, movieDetailFragment, MovieDetailFragment.TAG).commit();
        } else if (detailType.equals(Constants.TV_SHOW_DETAIL_TYPE)) {
            int detailId = getIntent().getIntExtra(Constants.TV_SHOW_ID, 0);
            TvShowDetailFragment tvShowDetailFragment = TvShowDetailFragment.newInstance(detailId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, tvShowDetailFragment, MovieDetailFragment.TAG).commit();
        }
    }

    private void setLayoutBehindStatusBar() {
        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
