package com.example.jitu.knowmovies.Data;

/**
 * Created by jitu on 15/12/17.
 */

public class Constants {
    //Recycler View
    public static final int numberOfColumns = 2;

    //JSON
    public static final String ID="id";
    public static final String RESULT = "results";
    public static final String TITLE = "title";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String POSTER_PATH = "poster_path";
    public static final String SYNOPSIS = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_BACKGROUND = "backdrop_path";

    //JSON PARSING
    public static final String API_URL = "https://api.themoviedb.org/3/movie/";
    public static final String POPULAR_MOVIE = "popular";
    public static final String TOP_MOVIE = "top_rated";
    public static final String UPCOMING_MOVIE = "upcoming";
    public static final String NOW_PLAYING = "now_playing";

    public static final String API = "?api_key=";
    //TODO(1) API KEY HERE
    public static final String API_KEY = "7f55d0a9a3def634de5223eb86e74ae4";
    public static final String PAGE_NO = "&page=";

    //Trailers
    public static final String VIDEOS="/videos";
    public static final String TRAILER_NAME="name";
    public static final String TRAILER_KEY="key";
    public static final String YOUTUBE_IMG_URL="http://img.youtube.com/vi/";
    public static final String YOUTUBE_THUMNAIL_IMG_URL="/0.jpg";
    public static final String YOUTUBE_URL="http://www.youtube.com/watch?v=";


    //IMAGE
    public static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    public static final String IMG_SIZE = "w342//";


}
