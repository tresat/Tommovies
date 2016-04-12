package com.tomtresansky.tommovies.net;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ttresans on 4/7/2016.
 */
public class MovieData implements Parcelable {
  private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
  private static final String DEFAULT_SIZE = "w185";

  private final String title;
  private final String posterFileName;

  public MovieData(String title, String posterFileName) {
    this.title = title;
    this.posterFileName = posterFileName.replaceFirst("/", "");
  }

  public String getTitle() {
    return title;
  }

  public String getPosterURL() {
    return Uri.parse(BASE_POSTER_URL)
        .buildUpon()
        .appendPath(DEFAULT_SIZE)
        .appendPath(posterFileName)
        .build()
        .toString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(posterFileName);
  }

  public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
    @Override
    public MovieData createFromParcel(Parcel in) {
      String title = in.readString();
      String posterFileName = in.readString();

      return new MovieData(title, posterFileName);
    }

    @Override
    public MovieData[] newArray(int size) {
      return new MovieData[size];
    }
  };
}
