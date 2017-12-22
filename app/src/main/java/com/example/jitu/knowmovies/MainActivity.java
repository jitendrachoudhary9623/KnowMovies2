package com.example.jitu.knowmovies;

import android.annotation.SuppressLint;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private RecyclerView mRecyclerView;
    public List<Movie> movieData;
    private QueryBuilder qb;
    MovieAdapter adapter;
    ProgressBar pb;
    String resStr;
    //LOADERS
    public Bundle bundle;
    public static final int LOADER_UID=1;
    public static final String MOVIE_QUERY_URL="query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qb = new QueryBuilder();
        movieData = new ArrayList<Movie>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_poster);
        pb = (ProgressBar) findViewById(R.id.pb_main);

      //  new MovieData().execute(qb.BuildQuery(1));

        //Intialize the loader
        getSupportLoaderManager().initLoader(LOADER_UID,null,this);
        bundle=new Bundle();

        setupLoader(1);
        setActionBarTitle(R.string.sort_popular);


    }
    public void setupLoader(int cat_id)
    {
        bundle.putString(MOVIE_QUERY_URL,qb.BuildQuery(cat_id));
        LoaderManager loaderManager=getSupportLoaderManager();
        Loader<String> loader=loaderManager.getLoader(LOADER_UID);
        if(loader==null)
        {
            loaderManager.initLoader(LOADER_UID,bundle,this);
        }else
        {
            loaderManager.restartLoader(LOADER_UID,bundle,this);
        }
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
                setupLoader(1);
                setActionBarTitle(R.string.sort_popular);
                return true;
            case R.id.menu_top:
                //  DisplayToast("Top Rated Selected");
                clearRecylcerView();
               setupLoader(2);
               setActionBarTitle(R.string.sort_top);
                return true;
            case R.id.menu_upcoming:
                // DisplayToast("Top Rated Selected");
                clearRecylcerView();
               setupLoader(3);
               setActionBarTitle(R.string.sort_upcoming);
                return true;
            case R.id.menu_now_playing:
                // DisplayToast("Top Rated Selected");
                clearRecylcerView();
              setupLoader(4);
              setActionBarTitle(R.string.sort_now_playing);
                return true;
        }

        return false;
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

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        final OkHttpClient client = new OkHttpClient();

        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                clearListData();

                pb.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                Request.Builder builder = new Request.Builder();


                    builder.url(args.getString(MOVIE_QUERY_URL));
                    Request request = builder.build();

                    try {
                        Response response = client.newCall(request).execute();
                        resStr = response.body().string();
                        return resStr;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

            };

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            createJSON(data);
            pb.setVisibility(View.INVISIBLE);
            setupRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

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

  /*  class MovieData extends AsyncTask<String, Void, List<Movie>> {
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
*/

}
