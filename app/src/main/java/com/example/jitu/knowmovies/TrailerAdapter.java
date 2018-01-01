package com.example.jitu.knowmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitu.knowmovies.model.Movie;
import com.example.jitu.knowmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jitu on 29/12/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private List<Trailer> mDataSet;
    private Context mContext;


    public TrailerAdapter(Context mContext, List<Trailer> mDataSet) {
        this.mDataSet = mDataSet;
        this.mContext = mContext;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.youtube_thumbnail_layout, parent, false);
        TrailerViewHolder vh = new TrailerViewHolder(v);
        return  vh;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {
holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    class TrailerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.youtube_thumnail);
            title=(TextView)itemView.findViewById(R.id.youtube_thumbnail_title);
        }
        public void bind(final int position)
        {

            Picasso.with(mContext).load(mDataSet.get(position).getYOUTUBE_IMG())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);

            title.setText((mDataSet.get(position).getYOUTUBE_TITLE()));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDataSet.get(position).getYOUTUBE_URL()));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(mDataSet.get(position).getYOUTUBE_URL()));
                    try {
                        mContext.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        mContext.startActivity(webIntent);
                    }

                }
            });
        }
    }
}
