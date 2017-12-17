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
        }
        apiUrl.append(Constants.API);
        apiUrl.append(Constants.API_KEY);

        return apiUrl.toString();
    }
}
