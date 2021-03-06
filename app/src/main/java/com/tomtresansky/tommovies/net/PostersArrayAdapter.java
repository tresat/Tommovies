package com.tomtresansky.tommovies.net;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tomtresansky.tommovies.R;

import java.util.List;

public final class PostersArrayAdapter extends ArrayAdapter<MovieData> {
  private static final String LOG_TAG = PostersArrayAdapter.class.getSimpleName();

  /**
   * This is our own custom constructor (it doesn't mirror a superclass constructor).
   * The context is used to inflate the layout file, and the List is the data we want
   * to populate into the lists
   *
   * @param context        The current context. Used to inflate the layout file.
   * @param movieDatas A List of MovieData objects to display in a list
   */
  public PostersArrayAdapter(Activity context, List<MovieData> movieDatas) {
    // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
    // the second argument is used when the ArrayAdapter is populating a single TextView.
    // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
    // going to use this second argument, so it can be any value. Here, we used 0.
    super(context, 0, movieDatas);
  }

  /**
   * Provides a view for an AdapterView (ListView, GridView, etc.)
   *
   * @param position    The AdapterView position that is requesting a view
   * @param convertView The recycled view to populate.
   *                    (search online for "android view recycling" to learn more)
   * @param parent The parent ViewGroup that is used for inflation.
   * @return The View for the position in the AdapterView.
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
    MovieData movieData = getItem(position);
    Context context = getContext();

    // Adapters recycle views to AdapterViews.
    // If this is a new View object we're getting, then inflate the layout.
    // If not, this view already has the layout inflated from a previous call to getView,
    // and we modify the View widgets as usual.
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.poster_layout, parent, false);
    }

    ImageView posterView = (ImageView) convertView.findViewById(R.id.poster_image);
    String posterURL = movieData.getPosterURL();
    Picasso.with(context).load(posterURL).into(posterView);

    TextView versionNameView = (TextView) convertView.findViewById(R.id.poster_text);
    versionNameView.setText(movieData.getTitle());

    return convertView;
  }
}
