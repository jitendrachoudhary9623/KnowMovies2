package com.example.jitu.knowmovies;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jitu.knowmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    Movie movie;
    ImageView movieBackground;
    ImageView moviePoster;
    TextView movieTitle;
    TextView movieOverview;
    TextView userVote;
    TextView releaseDate;
    Context context = DetailActivity.this;

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
        movieOverview.setText("Plot\n\n" + movie.getOverView());
        userVote.setText("Ratings\n" + movie.getUserRating() + "/10");
        releaseDate.setText("Release Date \n" + movie.getReleaseDate());

    }

}
