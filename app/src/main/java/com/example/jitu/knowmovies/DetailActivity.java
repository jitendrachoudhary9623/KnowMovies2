package com.example.jitu.knowmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitu.knowmovies.Data.Constants;
import com.example.jitu.knowmovies.Utility.QueryBuilder;
import com.example.jitu.knowmovies.model.Movie;
import com.example.jitu.knowmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    Movie movie;
    ImageView movieBackground;
    ImageView moviePoster;
    TextView movieTitle;
    TextView movieOverview;
    TextView userVote;
    TextView releaseDate;
    Context context = DetailActivity.this;
    RecyclerView rv_trailer;
    TrailerAdapter Trailer_adapter;
    List<Trailer> trailers;
    ProgressBar pb_trailers;
    public static final String TRAILER_LIST="trailer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movieBackground = (ImageView) findViewById(R.id.movie_background);
        moviePoster = (ImageView) findViewById(R.id.movie_poster);
        movieTitle = (TextView) findViewById(R.id.movie_title);
        movieOverview = (TextView) findViewById(R.id.movie_overview);
        userVote = (TextView) findViewById(R.id.movie_user_vote);
        releaseDate = (TextView) findViewById(R.id.movie_release_date);
        pb_trailers=(ProgressBar)findViewById(R.id.pb_trailers);
        rv_trailer=(RecyclerView)findViewById(R.id.rv_trailers);
        rv_trailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));        trailers=new ArrayList<Trailer>();

        movie = (Movie) getIntent().getParcelableExtra("Object");
       getSupportActionBar().setTitle(movie.getTitle());

        Picasso.with(context).load(movie.getMoviePoster())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(moviePoster);
        Picasso.with(context).load(movie.getBackdropPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(movieBackground);

        movieTitle.setText(movie.getTitle());
        movieOverview.setText("Plot\n\n" + movie.getOverView()+" \n");
        userVote.setText("Ratings\n" + movie.getUserRating() + "/10");
        releaseDate.setText("Release Date \n" + movie.getReleaseDate());
        QueryBuilder qb = new QueryBuilder();
        if(savedInstanceState==null)
        {
            new FetchTrailers().execute(qb.getTrailer(movie.getId()));
        }
        else
        {
            if(!trailers.isEmpty()) {
                trailers.clear();
                trailers=null;
            }
            trailers=new ArrayList<Trailer>();
                trailers=savedInstanceState.getParcelableArrayList(TRAILER_LIST);
           //setupRecycler();
        }
    }

    public void setupRecycler()
    {

        Trailer_adapter = new TrailerAdapter(DetailActivity.this,trailers);
        rv_trailer.setAdapter(Trailer_adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TRAILER_LIST, (ArrayList<Trailer>) trailers);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(!trailers.isEmpty())
        {
            trailers.clear();
        }
        trailers=savedInstanceState.getParcelableArrayList(TRAILER_LIST);
        setupRecycler();
    }

    public class FetchTrailers extends AsyncTask<String,Void,List<Trailer>> {
        OkHttpClient client = new OkHttpClient();
        String resStr=null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb_trailers.setVisibility(View.VISIBLE);
        }

        @Override
        protected List doInBackground(String... strings) {

            Request.Builder builder = new Request.Builder();

            builder.url(strings[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                resStr = response.body().string();

                createJSON(resStr);
            } catch (Exception e) {
                e.printStackTrace();

            }

            return trailers;
        }

        @Override
        protected void onPostExecute(List<Trailer> list) {
            super.onPostExecute(list);
            //return trailers;
            pb_trailers.setVisibility(View.INVISIBLE);

            setupRecycler();
        }

        private void createJSON(String JsonResult) throws JSONException {

            JSONObject root = new JSONObject(JsonResult);
            JSONArray result = root.getJSONArray(Constants.RESULT);
            Trailer trailer;
            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                trailer=new Trailer();
                trailer.setYOUTUBE_TITLE(object.getString(Constants.TRAILER_NAME));
                trailer.setYOUTUBE_KEY(object.getString(Constants.TRAILER_KEY));
                trailer.setYOUTUBE_IMG(prepareImgLink(trailer.getYOUTUBE_KEY()));
                trailer.setYOUTUBE_URL(prepareUrlLink(trailer.getYOUTUBE_KEY()));
                trailers.add(trailer);
            }
        }

        private String prepareImgLink(String Key)
        {//http://img.youtube.com/vi/<insert-youtube-video-id-here>/0.jpg

            return new StringBuilder().append(Constants.YOUTUBE_IMG_URL).append(Key).append(Constants.YOUTUBE_THUMNAIL_IMG_URL).toString();

        }
        private String prepareUrlLink(String Key)
        {
            // http://www.youtube.com/watch?v=" + id
            return new StringBuilder().append(Constants.YOUTUBE_URL).append(Key).toString();
        }

    }

}
