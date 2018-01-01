package com.example.jitu.knowmovies;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.Context;

import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitu.knowmovies.Data.Constants;
import com.example.jitu.knowmovies.Utility.QueryBuilder;
import com.example.jitu.knowmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //saved instance
    public static final String SAVED_INSTANCE_KEY = "key";
    public static final int LOADER_UID = 1;
    public static final String MOVIE_QUERY_URL = "query";
    public List<Movie> movieData;
    //LOADERS
    public Bundle bundle;
    MovieAdapter adapter;
    ProgressBar pb;
    String resStr;
    private RecyclerView mRecyclerView;
    private QueryBuilder qb;
    Context mainActivity = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qb = new QueryBuilder();
        movieData = new ArrayList<Movie>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_poster);
        pb = (ProgressBar) findViewById(R.id.pb_main);
        //  new MovieData().execute(qb.BuildQuery(1));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mainActivity, Constants.numberOfColumns));



        if(savedInstanceState==null) {
            setActionBarTitle(R.string.sort_popular);
            new MovieData().execute(qb.BuildQuery(1));
        }
        else
        {
            movieData.clear();
            movieData=null;
            movieData=savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_KEY);
            setupRecyclerView(movieData);
        }
    }



    public void setupRecyclerView(List<Movie> movieData) {

        adapter = new MovieAdapter(mainActivity, movieData);
        mRecyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*

    Displays the Toast Method
     */
    public void DisplayToast(String msg) {
        Context main = MainActivity.this;
        Toast.makeText(main, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        switch (itemSelected) {
            case R.id.menu_popular:
                // DisplayToast("Popular Selected");
                clearRecylcerView();
                //setupLoader(1);
                new MovieData().execute(qb.BuildQuery(1));

                setActionBarTitle(R.string.sort_popular);
                return true;
            case R.id.menu_top:
                //  DisplayToast("Top Rated Selected");
                clearRecylcerView();
               // setupLoader(2);
                new MovieData().execute(qb.BuildQuery(2));

                setActionBarTitle(R.string.sort_top);
                return true;
            case R.id.menu_upcoming:
                // DisplayToast("Top Rated Selected");
                clearRecylcerView();
                new MovieData().execute(qb.BuildQuery(3));

                //   setupLoader(3);
                setActionBarTitle(R.string.sort_upcoming);
                return true;
            case R.id.menu_now_playing:
                // DisplayToast("Top Rated Selected");
                clearRecylcerView();
               // setupLoader(4);
                new MovieData().execute(qb.BuildQuery(4));

                setActionBarTitle(R.string.sort_now_playing);
                return true;
        }

        return false;
    }


    void setActionBarTitle(int title) {
        getSupportActionBar().setTitle(title);

    }

    void clearRecylcerView() {
        clearListData();

        //  adapter.notifyDataSetChanged();
    }

    private void clearListData() {
        if (movieData != null) {
            movieData.clear();
        }
    }


    private String appendData(String imgPath) {
        return new StringBuilder()
                .append(Constants.BASE_IMG_URL)
                .append(Constants.IMG_SIZE)
                .append(imgPath)
                .toString();
    }

    class MovieData extends AsyncTask<String, Void, List<Movie>> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            clearListData();

            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if(!isNetworkConnected())
                return null;
            Request.Builder builder = new Request.Builder();
            for(int i=0;i<3;i++) {
                String page=Constants.PAGE_NO+i;
                builder.url(strings[0]+=page);
                Request request = builder.build();

                try {
                    Response response = client.newCall(request).execute();
                    resStr = response.body().string();

                    createJSON(resStr);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            return movieData;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            pb.setVisibility(View.INVISIBLE);
            if(movies==null) {
                DisplayToast("Unable To Connect To Internet");
            }
            else
            setupRecyclerView(movieData);
            //  DisplayToast(movieData.get(0).toString());
        }

        private boolean isNetworkConnected() {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE); // 1
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
            return networkInfo != null && networkInfo.isConnected(); // 3
        }

        private void createJSON(String JsonResult) throws JSONException {

            JSONObject root = new JSONObject(JsonResult);
            JSONArray result = root.getJSONArray(Constants.RESULT);
//DisplayToast(result.toString());
            Movie movie;
            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                movie = new Movie();
                movie.setId(object.getLong(Constants.ID));
                movie.setMoviePoster(appendData(object.getString(Constants.POSTER_PATH)));
                movie.setOverView(object.getString(Constants.SYNOPSIS));
                movie.setReleaseDate(object.getString(Constants.RELEASE_DATE));
                movie.setTitle(object.getString(Constants.TITLE));
                movie.setBackdropPath(appendData(object.getString(Constants.POSTER_BACKGROUND)));
                movie.setUserRating(object.getDouble(Constants.VOTE_AVERAGE));
                movieData.add(movie);
            }
        }

        private String appendData(String imgPath) {
            return new StringBuilder()
                    .append(Constants.BASE_IMG_URL)
                    .append(Constants.IMG_SIZE)
                    .append(imgPath)
                    .toString();
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_INSTANCE_KEY, (ArrayList<Movie>) movieData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movieData=savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_KEY);
    }
}
