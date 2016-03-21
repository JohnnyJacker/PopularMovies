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

import android.provider.BaseColumns;

/**
 * Defines table and column names for the movie database.
 */

public class MovieContract {


    /* Inner class that defines the contents of the movie table */
    public static final class MovieEntry implements BaseColumns {

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

    }
}
