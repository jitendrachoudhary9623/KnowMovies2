package com.example.jitu.knowmovies.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jitu on 13/1/18.
 */

public class FavoriteContract {

    public static final String AUTHORITY="com.example.jitu.knowmovies";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+AUTHORITY);
    public static final String PATH="MOVIES";
    public static class FavoriteEntry implements BaseColumns{

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
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
