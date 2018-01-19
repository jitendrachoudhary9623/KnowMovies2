package com.example.jitu.knowmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.jitu.knowmovies.db.FavoriteContract;
import com.example.jitu.knowmovies.db.MovieHelper;

import static com.example.jitu.knowmovies.db.FavoriteContract.FavoriteEntry.TABLE_NAME;

public class MovieContentProvider extends ContentProvider {

    public static final int MOVIES_ALL=999;
    public static final int MOVIES_ONE=1;
    private MovieHelper helper;
public static final UriMatcher sURI_MATCHER=buildUriMatcher();
    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavoriteContract.AUTHORITY,FavoriteContract.PATH,MOVIES_ALL);
        uriMatcher.addURI(FavoriteContract.AUTHORITY,FavoriteContract.PATH+"/#",MOVIES_ONE);
        return uriMatcher;
    }
    public MovieContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = helper.getWritableDatabase();

        int match = sURI_MATCHER.match(uri);
        int moviesDeleted=0;

        switch (match) {
            case MOVIES_ONE:
                String id = uri.getPathSegments().get(1);
                moviesDeleted = db.delete(TABLE_NAME, FavoriteContract.FavoriteEntry.MOVIE_ID+"=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (moviesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return moviesDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        int match = sURI_MATCHER.match(uri);

        switch (match) {
            case MOVIES_ALL:
                return "vnd.android.cursor.dir/vnd.com.example.jitu.knowmovies.MOVIES";
            case MOVIES_ONE:
                return "vnd.android.cursor.item/vnd.com.example.jitu.knowmovies.MOVIES";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db=helper.getReadableDatabase();


        int match = sURI_MATCHER.match(uri);
        Uri returnUri=null;
        switch (match) {
            case MOVIES_ALL:

                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
        }
        return  returnUri;
    }

    @Override
    public boolean onCreate() {
        helper=new MovieHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = helper.getReadableDatabase();

        int match = sURI_MATCHER.match(uri);
        Cursor cursor;

        switch (match) {
            case MOVIES_ALL:
                cursor =  db.query(TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                break;
            case MOVIES_ONE:
                cursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
