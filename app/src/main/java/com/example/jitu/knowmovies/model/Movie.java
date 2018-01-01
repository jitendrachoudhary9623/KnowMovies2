package com.example.jitu.knowmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jitu on 15/12/17.
 */
   /*
    original title
    movie poster image thumbnail
    A plot synopsis (called overview in the api)
    user rating (called vote_average in the api)
    release date
    */
public class Movie implements Parcelable {


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return BackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        BackdropPath = backdropPath;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    public Movie(String title, String moviePoster, String overView, double userRating, String releaseDate, long id, String backdropPath) {
        this.title = title;
        this.moviePoster = moviePoster;
        this.overView = overView;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.id = id;
        BackdropPath = backdropPath;
    }

    String title;
    String moviePoster;
    String overView;
    double userRating;
    String releaseDate;
    long id;
    String BackdropPath;
    public Movie()
    {

    }

    protected Movie(Parcel in) {
        title = in.readString();
        moviePoster = in.readString();
        overView = in.readString();
        userRating = in.readDouble();
        releaseDate = in.readString();
        id = in.readLong();
        BackdropPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(moviePoster);
        dest.writeString(overView);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
        dest.writeLong(id);
        dest.writeString(BackdropPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
