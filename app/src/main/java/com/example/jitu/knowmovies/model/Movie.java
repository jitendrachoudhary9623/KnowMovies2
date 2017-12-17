package com.example.jitu.knowmovies.model;

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
public class Movie implements Serializable {
    public Movie(String title, String moviePoster, String overView, double userRating, String releaseDate, String backdropPath) {
        this.title = title;
        this.moviePoster = moviePoster;
        this.overView = overView;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        BackdropPath = backdropPath;
    }

    String title;
    String moviePoster;
    String overView;
    double userRating;
    String releaseDate;

    public String getBackdropPath() {
        return BackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        BackdropPath = backdropPath;
    }

    String BackdropPath;
    public Movie()
    {

    }
    public Movie(String title, String moviePoster, String overView, double userRating, String releaseDate) {
        this.title = title;
        this.moviePoster = moviePoster;
        this.overView = overView;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

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
}
