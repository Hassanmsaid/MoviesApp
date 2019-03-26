package com.example.moviesapp.Utils;

import android.provider.BaseColumns;

public class FavouriteContract {
    public static final class FavouriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favourites";
        public static final String COL_MOVIE_ID = "movie_id";
        public static final String COL_MOVIE_TITLE = "movie_title";
        public static final String COL_MOVIE_RATING = "movie_rating";
        public static final String COL_VOTE_COUNT = "vote_count";
        public static final String COL_RELEASE_DATE = "release_date";
        public static final String COL_POSTER_PATH = "poster_path";
        public static final String COL_MOVIE_OVERVIEW = "movie_overview";
    }
}
