package com.tomtresansky.tommovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case R.id.action_refresh:
        refreshDisplay();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView =  inflater.inflate(R.layout.fragment_posters, container, false);

    postersAdapter = new PostersArrayAdapter(getActivity(), new ArrayList<MovieData>());

    GridView gridView = (GridView) rootView.findViewById(R.id.posters_grid);
    gridView.setAdapter(postersAdapter);

    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MovieData movieData = postersAdapter.getItem(i);

        Intent startDetailIntent = new Intent(getActivity(), MovieActivity.class)
            .putExtra(MovieActivity.DATA_KEY, movieData);

        // Verify that the intent will resolve to an activity
        if (null != startDetailIntent.resolveActivity(getActivity().getPackageManager())) {
          startActivity(startDetailIntent);
        }
      }
    });

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshDisplay();
  }

  private void refreshDisplay() {
    FetchPostersTask task = new FetchPostersTask(getActivity(), postersAdapter);
    task.execute();
  }
}
