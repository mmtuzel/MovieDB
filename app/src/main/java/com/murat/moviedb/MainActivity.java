package com.murat.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.murat.moviedb.databinding.ActivityMainBinding;
import com.murat.moviedb.detail.DetailActivity;
import com.murat.moviedb.movies.MoviesFragment;
import com.murat.moviedb.profile.ProfileFragment;
import com.murat.moviedb.tv.TvShowsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MoviesFragment moviesFragment = new MoviesFragment();
        //TvFragment tvFragment = new TvFragment();
        getSupportFragmentManager().beginTransaction()
                .add(binding.fragmentContainer.getId(), moviesFragment, MoviesFragment.TAG).commit();

        binding.bottomNav.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    public void openMovieDetail(int movieId) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("movieId", movieId);
        startActivity(detailIntent);
    }

    private void navigateToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            navigationItemSelectedListener = menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_movies: {
                        //Toast.makeText(MainActivity.this, "Movies", Toast.LENGTH_SHORT).show();
                        navigateToFragment(new MoviesFragment(), false);
                        return true;
                    }
                    case R.id.navigation_tv: {
                        //Toast.makeText(MainActivity.this, "TV", Toast.LENGTH_SHORT).show();
                        navigateToFragment(new TvShowsFragment(), false);
                        return true;
                    }
                    case R.id.navigation_profile: {
                        //Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        navigateToFragment(new ProfileFragment(), false);
                        return true;
                    }
                }
                return false;
            };
}
