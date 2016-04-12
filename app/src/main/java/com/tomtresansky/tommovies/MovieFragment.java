package com.tomtresansky.tommovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomtresansky.tommovies.net.MovieData;

/**
 * Created by ttresans on 4/11/2016.
 */
public class MovieFragment extends Fragment {
  private static final String LOG_TAG = MovieFragment.class.getSimpleName();

  private MovieData movieData;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView =  inflater.inflate(R.layout.fragment_movie, container, false);

    Intent intent = getActivity().getIntent();
    if (null != intent) {
      if (intent.hasExtra(MovieActivity.DATA_KEY)) {
        movieData = intent.getParcelableExtra(MovieActivity.DATA_KEY);
        TextView detailTextView = (TextView) rootView.findViewById(R.id.detail_text);
        detailTextView.setText(movieData.getTitle());
      } else {
        Log.d(LOG_TAG, "No movie data provided in intent!");
      }
    } else {
      Log.d(LOG_TAG, "Started without intent!");
    }

    return rootView;
  }
}
