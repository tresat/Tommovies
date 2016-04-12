package com.tomtresansky.tommovies.net;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.tomtresansky.tommovies.BuildConfig;
import com.tomtresansky.tommovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ttresans on 4/6/2016.
 */
public class FetchPostersTask extends AsyncTask<Void, Void, List<MovieData>> {
  private final String LOG_TAG = FetchPostersTask.class.getSimpleName();

  private final String BASE_API_URL = "https://api.themoviedb.org/3/";
  private final String MOVIE_PATH = "movie";
  private final String YEAR = "year";
  private final String API_KEY = "api_key";

  private final Activity activity;
  private final PostersArrayAdapter postersAdapter;
  private final String sortBy;

  public FetchPostersTask(Activity activity, final PostersArrayAdapter postersAdapter) {
    this.activity = activity;
    this.postersAdapter = postersAdapter;

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
    sortBy = prefs.getString(
        activity.getString(R.string.pref_sort_key),
        activity.getString(R.string.pref_sort_default));
  }

  @Override
  protected void onPostExecute(List<MovieData> movieDatas) {
    super.onPostExecute(movieDatas);

    postersAdapter.clear();

    if (null != movieDatas) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        postersAdapter.addAll(movieDatas);
      } else {
        postersAdapter.setNotifyOnChange(false);
        for (MovieData s : movieDatas) {
          postersAdapter.add(s);
        }
        postersAdapter.setNotifyOnChange(true);
        postersAdapter.notifyDataSetChanged();
      }
    }
  }

  @Override
  public List<MovieData> doInBackground(Void... params) {
    // These two need to be declared outside the try/catch
    // so that they can be closed in the finally block.
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;

    try {
      Uri uri = Uri.parse(BASE_API_URL)
          .buildUpon()
          .appendPath(MOVIE_PATH)
          .appendPath(sortBy)
          .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
          .appendQueryParameter(YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))
          .build();
      URL url = new URL(uri.toString());

      // Create the request and open the connection
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      // Read the input stream into a String
      InputStream inputStream = urlConnection.getInputStream();
      StringBuffer buffer = new StringBuffer();
      if (inputStream == null) {
        // Nothing to do.
        return null;
      }
      reader = new BufferedReader(new InputStreamReader(inputStream));

      String line;
      while ((line = reader.readLine()) != null) {
        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
        // But it does make debugging a *lot* easier if you print out the completed
        // buffer for debugging.
        buffer.append(line + "\n");
      }

      if (buffer.length() == 0) {
        // Stream was empty.  No point in parsing.
        return null;
      }

      return parseMovieData(buffer.toString());
    } catch (IOException | JSONException e) {
      Log.e(LOG_TAG, "Error ", e);
      // If the code didn't successfully get the weather data, there's no point in attemping
      // to parse it.
      return null;
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
          Log.e(LOG_TAG, "Error closing stream", e);
        }
      }
    }
  }

  private List<MovieData> parseMovieData(String jsonStr) throws JSONException {
    JSONObject json = new JSONObject(jsonStr);
    JSONArray results = json.getJSONArray("results");

    List<MovieData> movieDatas = new ArrayList<>(results.length());
    for (int i = 0, lim = results.length(); i < lim; i++) {
      JSONObject result = (JSONObject) results.get(i);

      String title = result.getString("title");
      String posterFileName = result.getString("poster_path");
      movieDatas.add(new MovieData(title, posterFileName));
    }

    return movieDatas;
  }
}
