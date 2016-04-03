/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Defines table and column names for the movie database.
 */

public class MovieContract {

    // The "Content Authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the device
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    //  Base paths for the URIs they match the names of the tables
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_TRAILER = "trailer";
    public static final String PATH_REVIEW = "review";
    public static final String PATH_FAVORITE = "favorite";


    /* Inner class that defines the contents of the movie table */
    public static final class MovieEntry implements BaseColumns {

        // This represents the base location for each table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        // Name of the movie table
        public static final String TABLE_NAME = "movie";

        // Name of the title column in the movie table
        public static final String COLUMN_TITLE = "title";

        // Name of the release date column in the movie table
        public static final String COLUMN_RELEASE = "release";

        // Name of the rating column in the movie table
        public static final String COLUMN_RATING = "rating";

        // Name of the overview column in the movie table
        public static final String COLUMN_OVERVIEW = "overview";

        // Name of the image path column in the movie table
        public static final String COLUMN_IMAGE_PATH = "image_path";

        // Name of the trailer column in the movie table
        public static final String COLUMN_MOVIE_ID = "movie_id";


        // This function helps build the content provider queries
        // Makes fewer places in the code aware of actual URI encoding
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the contents of the trailer table */
    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        // Name of the trailer table
        public static final String TABLE_NAME = "trailer";

        // Name of the title column in the movie table
        public static final String COLUMN_MOVIE_ID = "movie_id";

        // Name of the release date column in the movie table
        public static final String COLUMN_TRAILER_KEY = "trailer_key";


        public static Uri buildTrailerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the contents of the review table */
    public static final class ReviewEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        // Name of the trailer table
        public static final String TABLE_NAME = "review";

        // Name of the title column in the movie table
        public static final String COLUMN_MOVIE_ID = "movie_id";

        // Name of the release date column in the movie table
        public static final String COLUMN_AUTHOR = "author";

        // Name of the title column in the movie table
        public static final String COLUMN_REVIEW = "review";



        public static Uri buildReviewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /* Inner class that defines the contents of the review table */
    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        // Name of the trailer table
        public static final String TABLE_NAME = "favorite";

        // Name of the title column in the movie table
        public static final String COLUMN_MOVIE_ID = "movie_id";


        public static Uri buildFavoriteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
