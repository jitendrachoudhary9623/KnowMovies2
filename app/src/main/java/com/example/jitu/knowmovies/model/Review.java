package com.example.jitu.knowmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jitu on 2/1/18.
 */

public class Review implements Parcelable {
    public Review(){}

    String id;
    String Author;
    String Content;
    String Url;

    protected Review(Parcel in) {
        id = in.readString();
        Author = in.readString();
        Content = in.readString();
        Url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(Author);
        dest.writeString(Content);
        dest.writeString(Url);
    }

    public Review(String id, String author, String content, String url) {
        this.id = id;
        Author = author;
        Content = content;
        Url = url;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public static Creator<Review> getCREATOR() {
        return CREATOR;
    }
}
