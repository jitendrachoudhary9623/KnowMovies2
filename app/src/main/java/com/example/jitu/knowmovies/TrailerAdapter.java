package com.example.jitu.knowmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jitu on 29/12/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class TrailerViewHolder extends RecyclerView.ViewHolder{

        public TrailerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
