package com.example.moviesapp.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviesapp.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDBHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "favourites";
    private static final String COL_MOVIE_ID = "movie_id";
    private static final String COL_MOVIE_TITLE = "movie_title";
    private static final String COL_MOVIE_RATING = "movie_rating";
    private static final String COL_VOTE_COUNT = "vote_count";
    private static final String COL_RELEASE_DATE = "release_date";
    private static final String COL_POSTER_PATH = "poster_path";
    private static final String COL_MOVIE_OVERVIEW = "movie_overview";

    private static final String DB_NAME = "favourites_db";
    private static final int DB_VERSION = 1;
    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    public FavouriteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_FAVOURITE_TABLE =
                "Create table " + TABLE_NAME + " (" +
                        COL_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_MOVIE_TITLE + " TEXT, " +
                        COL_MOVIE_OVERVIEW + " TEXT, " +
                        COL_MOVIE_RATING + " REAL, " +
                        COL_VOTE_COUNT + " INTEGER, " +
                        COL_POSTER_PATH + " TEXT, " +
                        COL_RELEASE_DATE + " INTEGER );";
        database.execSQL(CREATE_FAVOURITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    private void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void addFavourite(Movie movie) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_MOVIE_ID, movie.getId());
        values.put(COL_MOVIE_TITLE, movie.getTitle());
        values.put(COL_POSTER_PATH, movie.getPoster_path());
        values.put(COL_MOVIE_RATING, movie.getVote_average());
        values.put(COL_VOTE_COUNT, movie.getVote_count());
        values.put(COL_MOVIE_OVERVIEW, movie.getOverview());
        values.put(COL_RELEASE_DATE, movie.getRelease_date());
        database.insert(TABLE_NAME, null, values);
        close();
    }

    public void deleteFavourite(int id) {
        open();
        database.delete(TABLE_NAME, COL_MOVIE_ID + "=" + id, null);
    }

    public List<Movie> getAllFavourites() {
        String[] columns = {COL_MOVIE_ID, COL_MOVIE_TITLE, COL_POSTER_PATH, COL_MOVIE_RATING,
                COL_VOTE_COUNT, COL_MOVIE_OVERVIEW, COL_RELEASE_DATE};
        List<Movie> favouriteList = new ArrayList<>();
        database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, columns,
                null, null, null, null, COL_MOVIE_ID);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();

                movie.setId(cursor.getString(cursor.getColumnIndex(COL_MOVIE_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(COL_MOVIE_TITLE)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(COL_POSTER_PATH)));
                movie.setVote_average(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_MOVIE_RATING))));
                movie.setVote_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_VOTE_COUNT))));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(COL_MOVIE_OVERVIEW)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndex(COL_RELEASE_DATE)));

                favouriteList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return favouriteList;
    }
}
