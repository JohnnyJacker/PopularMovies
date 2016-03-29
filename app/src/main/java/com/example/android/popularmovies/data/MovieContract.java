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


    public static final String PATH_MOVIE = "movie";


    /* Inner class that defines the contents of the movie table */
    public static final class MovieEntry implements BaseColumns {

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
        public static final String COLUMN_TRAILERS = "trailer";

        // Name of the reviews column in the movie table
        public static final String COLUMN_REVIEWS = "reviews";

        // Name of the favorite column in the movie table
        public static final String COLUMN_FAVORITE = "favorite";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
