package com.example.jitu.knowmovies.Utility;

/**
 * Created by jitu on 15/12/17.
 */
import com.example.jitu.knowmovies.Data.Constants;

import java.util.StringTokenizer;

public class QueryBuilder {
    public QueryBuilder() {
    }

    /*
    cat_id=1  Popular_movies;
    cat_id=2 Top_rated movies;
    cat_id=3 Upcoming movies;
    cat_id=4 Now_playing movies
     */
    public String BuildQuery(int cat_id)
    {
        StringBuilder apiUrl=new StringBuilder();
        apiUrl.append(Constants.API_URL);
        switch(cat_id)
        {
            case 1:
                apiUrl.append(Constants.POPULAR_MOVIE);
                break;
            case 2:
                apiUrl.append(Constants.TOP_MOVIE);
                break;
            case 3:
                apiUrl.append(Constants.UPCOMING_MOVIE);
                break;
            case 4:
                apiUrl.append(Constants.NOW_PLAYING);
                break;
        }
        apiUrl.append(Constants.API);
        apiUrl.append(Constants.API_KEY);
        apiUrl.append(Constants.PAGE_NO);
        return apiUrl.toString();
    }
//https://api.themoviedb.org/3/movie/181808/videos?api_key=7f55d0a9a3def634de5223eb86e74ae4
    public static String getTrailer(long id)
    {
        StringBuilder apiUrl=new StringBuilder();
        apiUrl.append(Constants.API_URL);
        apiUrl.append(id);
        apiUrl.append(Constants.VIDEOS);
        apiUrl.append(Constants.API);
        apiUrl.append(Constants.API_KEY);

        return apiUrl.toString();

    }
    public static String getReviewr(long id)
    {
        StringBuilder apiUrl=new StringBuilder();
        apiUrl.append(Constants.API_URL);
        apiUrl.append(id);
        apiUrl.append(Constants.REVIEW);
        apiUrl.append(Constants.API);
        apiUrl.append(Constants.API_KEY);

        return apiUrl.toString();

    }
}
