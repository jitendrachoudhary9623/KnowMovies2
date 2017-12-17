package com.example.jitu.knowmovies;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitu.knowmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jitu on 15/12/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviePoster> {
    private List<Movie> mDataSet;

    public MovieAdapter(Context mContext, List<Movie> mDataSet) {
        this.mDataSet = mDataSet;
        this.mContext = mContext;
    }

    private Context mContext;

    @Override
    public MoviePoster onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_grid_layout, parent, false);
        MoviePoster vh = new MoviePoster(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MoviePoster holder, final int position) {
        //holder.mTextView.setText(mDataSet[position]);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    class MoviePoster extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;

        public MoviePoster(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.movie_poster);
            title = (TextView) itemView.findViewById(R.id.rv_movie_title);
        }

        public void bind(final int position) {

            Picasso.with(mContext).load(mDataSet.get(position).getMoviePoster())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);

            title.setText((mDataSet.get(position).getTitle()));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Recycle Click" + position, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(mContext, DetailActivity.class);
                    Movie movie = mDataSet.get(position);
                    in.putExtra("Object", movie);
                    mContext.startActivity(in);

                }
            });


        }


    }
}
