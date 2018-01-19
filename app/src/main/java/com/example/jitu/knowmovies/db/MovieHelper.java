package com.example.jitu.knowmovies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.jitu.knowmovies.db.FavoriteContract.FavoriteEntry;
/**
 * Created by jitu on 13/1/18.
 */

public class MovieHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="movies.db";
    public static final int DATABASE_VERSION=6;

    public MovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_QUERY=" CREATE TABLE " + FavoriteEntry.TABLE_NAME
                                            +" ( "
                                         //   +FavoriteEntry._ID+" INTEGER NOT NULL PRIMARY KEY ,"
                                            +FavoriteEntry.MOVIE_ID +" INTEGER NOT NULL PRIMARY KEY, "
                                            +FavoriteEntry.MOVIE_TITLE+" TEXT NOT NULL ,"
                                            +FavoriteEntry.MOVIE_OVERVIEW+" TEXT NOT NULL ,"
                                            +FavoriteEntry.MOVIE_RATING+" REAL NOT NULL ,"
                                            +FavoriteEntry.MOVIE_RELEASE_DATE+" TEXT NOT NULL ,"
                                            +FavoriteEntry.MOVIE_POSTER+" TEXT NOT NULL ,"
                                            +FavoriteEntry.MOVIE_BACKDROP+" TEXT NOT NULL "
                                            + " ) ;";
    public static final String DROP_QUERY="DROP TABLE IF EXISTS "+FavoriteEntry.TABLE_NAME+";";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_QUERY);
        db.execSQL(CREATE_QUERY);
    }
}
