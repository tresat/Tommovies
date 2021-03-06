package com.tomtresansky.tommovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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

        ImageView posterView = (ImageView) rootView.findViewById(R.id.large_poster);
        String posterURL = movieData.getPosterURL();
        Picasso.with(getContext()).load(posterURL).into(posterView);

        TextView titleTextView = (TextView) rootView.findViewById(R.id.title);
        titleTextView.setText(movieData.getTitle());

        TextView ratingTextView = (TextView) rootView.findViewById(R.id.rating);
        ratingTextView.setText(movieData.getRating());

        TextView releaseTextView = (TextView) rootView.findViewById(R.id.release);
        releaseTextView.setText(movieData.getReleaseDate());

        TextView synopsisTextView = (TextView) rootView.findViewById(R.id.synopsis);
        synopsisTextView.setText(movieData.getSynopsis());
      } else {
        Log.d(LOG_TAG, "No movie data provided in intent!");
      }
    } else {
      Log.d(LOG_TAG, "Started without intent!");
    }

    return rootView;
  }
}
