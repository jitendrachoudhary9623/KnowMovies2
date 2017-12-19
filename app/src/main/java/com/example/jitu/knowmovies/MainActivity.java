package com.example.jitu.knowmovies;

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
import android.widget.Toast;

import com.example.jitu.knowmovies.Data.Constants;
import com.example.jitu.knowmovies.Utility.QueryBuilder;
import com.example.jitu.knowmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public List<Movie> movieData;
    private QueryBuilder qb;
    MovieAdapter adapter;
    ProgressBar pb;
    String resStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qb = new QueryBuilder();
        movieData = new ArrayList<Movie>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_poster);
        pb = (ProgressBar) findViewById(R.id.pb_main);

        new MovieData().execute(qb.BuildQuery(1));
        setActionBarTitle(R.string.sort_popular);


    }

    public void setupRecyclerView() {
        Context mainActivity = MainActivity.this;
        mRecyclerView.setLayoutManager(new GridLayoutManager(mainActivity, Constants.numberOfColumns));


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
                new MovieData().execute(qb.BuildQuery(1));
                setActionBarTitle(R.string.sort_popular);
                return true;
            case R.id.menu_top:
              //  DisplayToast("Top Rated Selected");
                clearRecylcerView();
                new MovieData().execute(qb.BuildQuery(2));
                setActionBarTitle(R.string.sort_top);
                return true;
            case R.id.menu_upcoming:
               // DisplayToast("Top Rated Selected");
                clearRecylcerView();
                new MovieData().execute(qb.BuildQuery(3));
                setActionBarTitle(R.string.sort_upcoming);
                return true;
            case R.id.menu_now_playing:
                // DisplayToast("Top Rated Selected");
                clearRecylcerView();
                new MovieData().execute(qb.BuildQuery(4));
                setActionBarTitle(R.string.sort_now_playing);
                return true;
        }

        return false;
    }
    void setUpMovies(int id)
    {
        switch(id)
        {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }
    }
    void setActionBarTitle(int title)
    {
        getSupportActionBar().setTitle(title);

    }
    void clearRecylcerView() {
        clearListData();
        adapter.notifyDataSetChanged();
      //  adapter.notifyDataSetChanged();
    }

    private void clearListData() {
        if (movieData != null) {
            movieData.clear();
        }
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
            Request.Builder builder = new Request.Builder();
            for(int i=0;i<5;i++) {
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
            setupRecyclerView();
            //  DisplayToast(movieData.get(0).toString());
        }

        private void createJSON(String JsonResult) throws JSONException {
            JSONObject root = new JSONObject(JsonResult);
            JSONArray result = root.getJSONArray(Constants.RESULT);
//DisplayToast(result.toString());
            Movie movie;
            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                movie = new Movie();
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


}
