package com.tomtresansky.tommovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.tomtresansky.tommovies.net.FetchPostersTask;
import com.tomtresansky.tommovies.net.MovieData;
import com.tomtresansky.tommovies.net.PostersArrayAdapter;

import java.util.ArrayList;

public class PostersFragment extends Fragment {
  private PostersArrayAdapter postersAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.posters_fragment, menu);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView =  inflater.inflate(R.layout.fragment_posters, container, false);

    postersAdapter = new PostersArrayAdapter(getActivity(), new ArrayList<MovieData>());

    refreshDisplay();

    GridView gridView = (GridView) rootView.findViewById(R.id.posters_grid);
    gridView.setAdapter(postersAdapter);

    return rootView;
  }

  private void refreshDisplay() {
    FetchPostersTask task = new FetchPostersTask(getActivity(), postersAdapter);
    task.execute();
  }
}
