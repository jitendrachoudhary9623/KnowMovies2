package com.example.jitu.knowmovies.db;

import android.provider.BaseColumns;

/**
 * Created by jitu on 13/1/18.
 */

public class FavoriteContract {

    public static class FavoriteEntry implements BaseColumns{
        /*

    String title;
    String moviePoster;
    String overView;
    double userRating;
    String releaseDate;
    long id;
    String BackdropPath;
         */
        public static final String TABLE_NAME="MOVIES";
        public static final String MOVIE_POSTER="movie_poster";
        public static final String MOVIE_OVERVIEW="movie_overview";
        public static final String MOVIE_TITLE="movie_title";
        public static final String MOVIE_BACKDROP="movie_backdrop";
        public static final String MOVIE_RATING="movie_rating";
        public static final String MOVIE_RELEASE_DATE="movie_release";
        public static final String MOVIE_ID="movie_id";



    }
}
