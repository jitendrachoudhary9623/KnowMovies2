package com.example.jitu.knowmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.example.jitu.knowmovies.db.FavoriteContract;
import com.example.jitu.knowmovies.model.Movie;
import com.example.jitu.knowmovies.model.Review;
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

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String mypreference = "mypref";
    public static final String TRAILER_LIST = "trailer";
    public static final String REVIEW_LIST = "review";
    Movie movie;
    ImageView movieBackground;
    ImageView moviePoster;
    TextView movieTitle;
    TextView movieOverview;
    TextView userVote;
    TextView releaseDate;
    Context context = DetailActivity.this;
    RecyclerView rv_trailer;
    RecyclerView rv_reviews;
    TrailerAdapter Trailer_adapter;
    ReviewAdapter reviewAdapter;
    List<Trailer> trailers;
    List<Review> reviews;
    ProgressBar pb_trailers;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

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
        pb_trailers = (ProgressBar) findViewById(R.id.pb_trailers);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        rv_trailer = (RecyclerView) findViewById(R.id.rv_trailers);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.contentPanel);
        rv_trailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailers = new ArrayList<Trailer>();

        rv_reviews = (RecyclerView) findViewById(R.id.rv_reviews);
        rv_reviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        reviews = new ArrayList<Review>();
        movie = (Movie) getIntent().getParcelableExtra("Object");
        getSupportActionBar().setTitle(movie.getTitle());

        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, MODE_PRIVATE);


        Picasso.with(context).load(movie.getMoviePoster())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(moviePoster);
        Picasso.with(context).load(movie.getBackdropPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(movieBackground);

        movieTitle.setText(movie.getTitle());
        movieOverview.setText("Plot\n\n" + movie.getOverView() + " \n");
        userVote.setText("Ratings\n" + movie.getUserRating() + "/10");
        releaseDate.setText("Release Date \n" + movie.getReleaseDate());
        QueryBuilder qb = new QueryBuilder();
        if (savedInstanceState == null) {
            new FetchTrailers().execute(qb.getTrailer(movie.getId()));
            new FetchReviews().execute(qb.getReviewr(movie.getId()));
        } else {

            trailers = null;

            trailers = savedInstanceState.getParcelableArrayList(TRAILER_LIST);
            reviews = savedInstanceState.getParcelableArrayList(REVIEW_LIST);
            Snackbar.make(coordinatorLayout, "Rotated", Snackbar.LENGTH_LONG).show();

            setupYouTubeRecycler();
            setupReviewRecycler();

        }
        if (sharedpreferences.contains("" + movie.getId())) {
            fab.setImageResource(R.drawable.movie_saved);
        } else {

            fab.setImageResource(R.drawable.movie_unsaved);


        }

    }

    public void setupYouTubeRecycler() {

        Trailer_adapter = new TrailerAdapter(DetailActivity.this, trailers);
        rv_trailer.setAdapter(Trailer_adapter);
    }

    public void setupReviewRecycler() {
        //   Toast.makeText(DetailActivity.this, "" + reviews.size(), Toast.LENGTH_LONG).show();
        reviewAdapter = new ReviewAdapter(DetailActivity.this, reviews);
        rv_reviews.setAdapter(reviewAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TRAILER_LIST, (ArrayList<Trailer>) trailers);
        outState.putParcelableArrayList(REVIEW_LIST, (ArrayList<Review>) reviews);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        trailers = savedInstanceState.getParcelableArrayList(TRAILER_LIST);

        reviews = savedInstanceState.getParcelableArrayList(REVIEW_LIST);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (sharedpreferences.contains("" + movie.getId())) {
                    removeFromDb();
                    editor = sharedpreferences.edit();
                    editor.remove("" + movie.getId());
                    editor.apply();
                    fab.setImageResource(R.drawable.movie_unsaved);
                } else {
                    saveToDb();
                    editor = sharedpreferences.edit();
                    editor.putBoolean("" + movie.getId(), true);
                    editor.commit();
                    fab.setImageResource(R.drawable.movie_saved);


                }
                break;
        }

    }

    public void saveToDb() {
        ContentValues cv = new ContentValues();
        Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;

        cv.put(FavoriteContract.FavoriteEntry.MOVIE_POSTER, movie.getMoviePoster());
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_TITLE, movie.getTitle());
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_OVERVIEW, movie.getOverView());
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_RATING, movie.getUserRating());
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_BACKDROP, movie.getBackdropPath());
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_ID, movie.getId());
        getContentResolver().insert(uri,
                cv);
        Snackbar.make(coordinatorLayout, getString(R.string.Movie_Saved), Snackbar.LENGTH_LONG).show();


    }

    public void removeFromDb() {
        Uri uri = Uri.parse(FavoriteContract.FavoriteEntry.CONTENT_URI + "/" + movie.getId());

        getContentResolver().delete(uri, FavoriteContract.FavoriteEntry.MOVIE_ID + "=" + movie.getId(), null);
        Snackbar.make(coordinatorLayout, getString(R.string.Movie_remove), Snackbar.LENGTH_LONG).show();
    }

    public class FetchTrailers extends AsyncTask<String, Void, List<Trailer>> {
        OkHttpClient client = new OkHttpClient();
        String resStr = null;

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

            setupYouTubeRecycler();
        }

        private void createJSON(String JsonResult) throws JSONException {

            JSONObject root = new JSONObject(JsonResult);
            JSONArray result = root.getJSONArray(Constants.RESULT);
            Trailer trailer;
            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                trailer = new Trailer();
                trailer.setYOUTUBE_TITLE(object.getString(Constants.TRAILER_NAME));
                trailer.setYOUTUBE_KEY(object.getString(Constants.TRAILER_KEY));
                trailer.setYOUTUBE_IMG(prepareImgLink(trailer.getYOUTUBE_KEY()));
                trailer.setYOUTUBE_URL(prepareUrlLink(trailer.getYOUTUBE_KEY()));
                trailers.add(trailer);
            }
        }

        private String prepareImgLink(String Key) {//http://img.youtube.com/vi/<insert-youtube-video-id-here>/0.jpg

            return new StringBuilder().append(Constants.YOUTUBE_IMG_URL).append(Key).append(Constants.YOUTUBE_THUMNAIL_IMG_URL).toString();

        }

        private String prepareUrlLink(String Key) {
            // http://www.youtube.com/watch?v=" + id
            return new StringBuilder().append(Constants.YOUTUBE_URL).append(Key).toString();
        }

    }

    class FetchReviews extends AsyncTask<String, Void, List<Review>> {
        OkHttpClient client = new OkHttpClient();
        String resStr = null;

        @Override
        protected List<Review> doInBackground(String... strings) {
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

            return reviews;
        }

        @Override
        protected void onPostExecute(List<Review> reviews1) {
            super.onPostExecute(reviews1);
            setupReviewRecycler();
        }

        private void createJSON(String JsonResult) throws JSONException {

            JSONObject root = new JSONObject(JsonResult);
            JSONArray result = root.getJSONArray(Constants.RESULT);
            Review review;
            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                review = new Review();
                review.setId(object.getString(Constants.ID));
                review.setAuthor(object.getString(Constants.AUTHOR));
                review.setContent(object.getString(Constants.CONTENT));
                review.setUrl(object.getString(Constants.REVIEW_URL));
                reviews.add(review);
            }
        }

    }
}
