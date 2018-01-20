package com.example.jitu.knowmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jitu.knowmovies.model.Review;

import java.util.List;

/**
 * Created by jitu on 2/1/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>
{
    List<Review> reviewList;
    Context mContext;
    public ReviewAdapter(Context context,List<Review> reviews)
    {
     mContext=context;
     reviewList=reviews;
    }
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.review_layout,parent,false);
        ReviewViewHolder vh=new ReviewViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView AuthorText;
        TextView ContentText;
        TextView ContentTextMore;
        TextView read_more;
        TextView read_less;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            AuthorText=(TextView)itemView.findViewById(R.id.review_author);
            ContentText=(TextView)itemView.findViewById(R.id.review_comment);
            ContentTextMore=(TextView)itemView.findViewById(R.id.review_comment_more);

        }
        public void bind(final int position)
        {
            AuthorText.setText(reviewList.get(position).getAuthor());
            ContentText.setText(reviewList.get(position).getContent());
            ContentTextMore.setText(reviewList.get(position).getContent());
            ContentTextMore.setVisibility(View.GONE);

            ContentText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(reviewList.get(position).getUrl()));
                    mContext.startActivity(i);*/
                    ContentText.setVisibility(View.GONE);
                    ContentTextMore.setVisibility(View.VISIBLE);
                }
            });
            ContentTextMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ContentTextMore.setVisibility(View.GONE);
                    ContentText.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
