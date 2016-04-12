package com.tomtresansky.tommovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public final class MovieActivity extends AppCompatActivity {
  public static final String DATA_KEY = String.format("%s/MOVIE_DATA", MovieActivity.class.getName());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.movie_container, new MovieFragment())
          .commit();
    }
  }
}
