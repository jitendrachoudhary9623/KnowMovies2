package com.example.jitu.knowmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jitu on 1/1/18.
 */

public class Trailer implements Parcelable
{

    public String getYOUTUBE_URL() {
        return YOUTUBE_URL;
    }

    public void setYOUTUBE_URL(String YOUTUBE_URL) {
        this.YOUTUBE_URL = YOUTUBE_URL;
    }

    public String getYOUTUBE_IMG() {
        return YOUTUBE_IMG;
    }

    public void setYOUTUBE_IMG(String YOUTUBE_IMG) {
        this.YOUTUBE_IMG = YOUTUBE_IMG;
    }

    public String getYOUTUBE_TITLE() {
        return YOUTUBE_TITLE;
    }

    public void setYOUTUBE_TITLE(String YOUTUBE_TITLE) {
        this.YOUTUBE_TITLE = YOUTUBE_TITLE;
    }

    public String getYOUTUBE_KEY() {
        return YOUTUBE_KEY;
    }

    public void setYOUTUBE_KEY(String YOUTUBE_KEY) {
        this.YOUTUBE_KEY = YOUTUBE_KEY;
    }

    public static Creator<Trailer> getCREATOR() {
        return CREATOR;
    }

    public Trailer(String YOUTUBE_URL, String YOUTUBE_IMG, String YOUTUBE_TITLE, String YOUTUBE_KEY) {
        this.YOUTUBE_URL = YOUTUBE_URL;
        this.YOUTUBE_IMG = YOUTUBE_IMG;
        this.YOUTUBE_TITLE = YOUTUBE_TITLE;
        this.YOUTUBE_KEY = YOUTUBE_KEY;
    }

    String YOUTUBE_URL;
   String YOUTUBE_IMG;
   String YOUTUBE_TITLE;
   String YOUTUBE_KEY;

   public Trailer(){}


    protected Trailer(Parcel in) {
        YOUTUBE_URL = in.readString();
        YOUTUBE_IMG = in.readString();
        YOUTUBE_TITLE = in.readString();
        YOUTUBE_KEY = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(YOUTUBE_URL);
        dest.writeString(YOUTUBE_IMG);
        dest.writeString(YOUTUBE_TITLE);
        dest.writeString(YOUTUBE_KEY);
    }
}
