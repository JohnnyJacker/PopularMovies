package com.example.android.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.MovieContract;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import java.util.concurrent.Callable;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {
    }

    public void putArgs(Bundle bundle) {
        this.extras = bundle;
    };

    public static MovieDetailFragment newInstance(Intent intent){
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.putArgs(intent.getExtras());
        return movieDetailFragment;
    };


    Boolean flag = false;
    ResponseTrailer videoResponseObj;
    ResponseReview reviewResponseObj;
    Gson gson;
    AsyncHttpClient client;
    LVAdapterTrailer trailerLVAdapter;
    ListView trailerListView;
    ListView reviewListView;
    String trailerURL;
    String reviewURL;
    String movie_id;
    Bundle extras = null;

    String trailerResponseStr;
    String reviewResponseStr;
    Context mContext;


    //  These bind the views to the member variables
    @Bind(R.id.title_text_view)
    TextView mTitle;
    @Bind(R.id.overview_text_view)
    TextView mOverview;
    @Bind(R.id.releasedate_text_view)
    TextView mRelease;
    @Bind(R.id.rating_text_view)
    TextView mRating;
    @Bind(R.id.imageView)
    ImageView mThumbnail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.fragment_detail, container, false);

        trailerListView = (ListView) rootview.findViewById(R.id.trailerListView);


        //  Not doing anything with the reviews list view  yet, may not even use a listview depending
        //  on how the data is presented and how I feel about it
//        reviewListView = (ListView) rootview.findViewById(R.id.review_list_view);


/**
 * OnItemClickListener for the trailer listview
 */
        trailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getTrailerAtPosition(position);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(trailerURL));
                startActivity(i);
            }
        });

        ButterKnife.bind(this, rootview);

        if (extras == null) {
            extras = getActivity().getIntent().getExtras();
        }

            final String movie_title = extras.getString("movie_title");
            final String movie_overview = extras.getString("movie_overview");
            final String movie_releasedate = extras.getString("movie_releasedate");
            final String movie_rating = extras.getString("movie_rating");
            final String movie_thumbnail = extras.getString("movie_thumbnail");


            //  Setting the text for the movie title from the passed in values from MoviesFragment
            mTitle.setText(movie_title);

            mOverview.setText(movie_overview);

            mRelease.setText(movie_releasedate);

            mRating.setText(movie_rating + "/10");

            Picasso.with(getContext())
                    .load(movie_thumbnail)
                    .resize(185, 256)
                    .centerInside()
                    .placeholder(R.drawable.noimage)
                    .into(mThumbnail);

            movie_id = extras.getString("movie_id");

            final ImageView star = (ImageView) rootview.findViewById(R.id.imageButton);

            //  Flag is set to false initially
            //  If the movie id returns false the image is off and the flag gets set to true
            if (flag == checkForMovie(movie_id)) {
                star.setImageResource(android.R.drawable.star_big_off);
                flag = true;
                //  If the movie id returns true then the star is on and the flag is again set to false.
            } else {
                star.setImageResource(android.R.drawable.star_big_on);
                flag = false;
            }






            /**
             * On click listener for the favorite star
             */
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    deleteFavoriteMovie(movie_id);

                    if (flag == checkForMovie(movie_id)) {


                        star.setImageResource(android.R.drawable.star_big_off);
                        Toast.makeText(getActivity(), "Unmarked as Favorite", Toast.LENGTH_LONG).show();


                        flag = true;

                    } else {

                        //  Here is where you are inserting values into
                        // the database using the content provider into the movie table
                        ContentValues testMovieValues = createMovieValues(movie_title, movie_thumbnail, movie_overview, movie_releasedate,
                                movie_rating, movie_id);

                        Log.d("MovieDetailFragment", movie_thumbnail);

                        TestContentObserver movieTco = getTestContentObserver();
                        getContext().getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, movieTco);


                        Uri movieUri = getContext().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, testMovieValues);

                        movieTco.waitForNotificationOrFail();
                        getContext().getContentResolver().unregisterContentObserver(movieTco);

                        long movieRowId = ContentUris.parseId(movieUri);

                        assertTrue(movieRowId != -1);


                        //  Here is where you are inserting values into
                        // the database using the content provider into the trailer table
                        ContentValues testTrailerValues = createTrailerValues(movie_id);

                        TestContentObserver trailerTco = getTestContentObserver();
                        getContext().getContentResolver().registerContentObserver(MovieContract.TrailerEntry.CONTENT_URI, true, trailerTco);

                        Uri trailerUri = getContext().getContentResolver().insert(MovieContract.TrailerEntry.CONTENT_URI, testTrailerValues);

                        trailerTco.waitForNotificationOrFail();
                        getContext().getContentResolver().unregisterContentObserver(trailerTco);

                        long trailerRowId = ContentUris.parseId(trailerUri);

                        assertTrue(trailerRowId != -1);

                        //  Here is where you are inserting values into
                        // the database using the content provider into the favorite table
                        ContentValues testFavoriteValues = createFavoriteValues(movie_id);

                        TestContentObserver favoriteTco = getTestContentObserver();
                        getContext().getContentResolver().registerContentObserver(MovieContract.FavoriteEntry.CONTENT_URI, true, favoriteTco);


                        Uri favoriteUri = getContext().getContentResolver().insert(MovieContract.FavoriteEntry.CONTENT_URI, testFavoriteValues);

                        favoriteTco.waitForNotificationOrFail();
                        getContext().getContentResolver().unregisterContentObserver(favoriteTco);

                        long favoriteRowId = ContentUris.parseId(favoriteUri);

                        assertTrue(favoriteRowId != -1);


                        star.setImageResource(android.R.drawable.star_big_on);
                        Toast.makeText(getActivity(), "Marked as Favorite", Toast.LENGTH_LONG).show();

                        flag = false;

                    }
                }
            });






//        String reviewString = getReviews();
//        TextView reviewTextView = (TextView) getActivity().findViewById(R.id.review_list_view);
//        reviewTextView.setText(reviewString);


        return rootview;
    }

    /**
     * @param title     title of the movie
     * @param imagePath image path for the movie poster
     * @param overview  movie overview
     * @param release   movie release date
     * @param rating    movie rating
     * @param movie_id  movie id assigned by the movies database TMDB
     * @return returns testMovieValues ContentValues
     */
    static ContentValues createMovieValues(String title, String imagePath, String overview, String release,
                                           String rating, String movie_id) {

        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, title);
        movieValues.put(MovieContract.MovieEntry.COLUMN_IMAGE_PATH, imagePath);
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview);
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE, release);
        movieValues.put(MovieContract.MovieEntry.COLUMN_RATING, rating);
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie_id);

        return movieValues;
    }

    ContentValues createTrailerValues(String movie_id) {

        if (trailerLVAdapter.getCount() >= 1) {

            //Each trailer?
            ResponseTrailer.ResultsEntity item = (ResponseTrailer.ResultsEntity) trailerLVAdapter.getItem(0);
            trailerURL = "https://www.youtube.com/watch?v=" + item.getKey();

        } else {
            trailerURL = "No Trailer Available";
        }

        ContentValues testTrailerValues = new ContentValues();

        testTrailerValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, movie_id);
        testTrailerValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_KEY,
                trailerURL);

        return testTrailerValues;
    }

    //TODO:  Create createReviewValues here


//        static ContentValues createReviewValues() {
//
//
//
//        ContentValues testReviewValues = new ContentValues();
//
//        testReviewValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID, "385497");
//        testReviewValues.put(MovieContract.ReviewEntry.COLUMN_AUTHOR, "Christopher Paris");
//        testReviewValues.put(MovieContract.ReviewEntry.COLUMN_REVIEW, "This is one of my favorite films ever." +
//        "It has everything a proper James Bond film should have.  Right from the very beginning" +
//                "people are being chased and shot at in an adrenaline filled intro." +
//                "Bond song is best in history of bond movies.  Love it and highly recommend it to anybody that" +
//                "has not had the pleasure.");
//
//
//        return testReviewValues;
//    }

    /**
     * @param movie_id movie id assigned by the movies database TMDB
     * @return returns testFavoriteValues ContentValues
     */
    static ContentValues createFavoriteValues(String movie_id) {

        ContentValues favoriteValues = new ContentValues();
        favoriteValues.put(MovieContract.FavoriteEntry.COLUMN_MOVIE_ID, movie_id);

        return favoriteValues;
    }

    /**
     * This gets the current position and builds the URL for the youtube trailer
     */
    public void getTrailerAtPosition(int position) {

        if (trailerLVAdapter.getCount() >= 1) {

            //Each trailer?
            ResponseTrailer.ResultsEntity item = (ResponseTrailer.ResultsEntity) trailerLVAdapter.getItem(position);
            trailerURL = "https://www.youtube.com/watch?v=" + item.getKey();

        } else {
            trailerURL = "No Trailer Available";
        }

    }


    //TODO:  Call this when you are ready to get the reviews
    public String getReviews() {

        for (int i = 0; i < trailerLVAdapter.getCount(); i++) {
            ResponseReview.ResultsEntity item = (ResponseReview.ResultsEntity) trailerLVAdapter.getItem(i);
            reviewURL = item.getContent();


        }
        return reviewURL;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateMovies();
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    public void updateMovies() {
        parseTrailerJson();
        parseReviewJson();
    }

    //  This method deletes a favorite movie
    //  Deletes data from all four tables
    //TODO:  Modify this to include the remaining table review

    /**
     * @param movie_id takes in a movie_id to delete all data from all tables if it's a favorite
     */
    public void deleteFavoriteMovie(String movie_id) {

        //  This is the where clause that looks for the movie_id in the movie_id column
        String movieWhereClause = MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=" + movie_id;

        //  This is getting the content resolver to delete from the movie table
        getContext().getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                movieWhereClause,
                null
        );
        Cursor movieCursor = getContext().getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Favorite table during delete", movieCursor.getCount(), movieCursor.getCount());
        movieCursor.close();

        //TODO:  Add the last table right inbetween the movieCursor.close() and the trailerWhereClause

        //  This is the where clause that looks for the movie_id in the movie_id column
        String trailerWhereClause = MovieContract.TrailerEntry.COLUMN_MOVIE_ID + "=" + movie_id;

        //  This is getting the content resolver to delete from the movie table
        getContext().getContentResolver().delete(
                MovieContract.TrailerEntry.CONTENT_URI,
                trailerWhereClause,
                null
        );
        Cursor trailerCursor = getContext().getContentResolver().query(
                MovieContract.TrailerEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Favorite table during delete", trailerCursor.getCount(), trailerCursor.getCount());
        trailerCursor.close();


        String favoriteWhereClause = MovieContract.FavoriteEntry.COLUMN_MOVIE_ID + "=" + movie_id;

        getContext().getContentResolver().delete(
                MovieContract.FavoriteEntry.CONTENT_URI,
                favoriteWhereClause,
                null
        );

        Cursor favoriteCursor = getContext().getContentResolver().query(
                MovieContract.FavoriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Favorite table during delete", favoriteCursor.getCount(), favoriteCursor.getCount());
        favoriteCursor.close();


    }

    /**
     * @return String Returns built url for fetching videos using the passed in movie id from
     * the main activity
     */
    public String builtTrailerURL() {



        String MOVIE_BASE_URL =
                "http://api.themoviedb.org/3/movie/";


        movie_id = extras.getString("movie_id");

//        String movie_id = "281957";

        String builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon() + movie_id + "/videos?&api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY;

        String url = builtUri.toString();

        return url;
    }


    /**
     * @return String Returns built url for fetching reviews using the passed in movie id from
     * the main activity
     */
    public String builtReviewURL() {

        String MOVIE_BASE_URL =
                "http://api.themoviedb.org/3/movie/";

//        String movie_id = getActivity().getIntent().getExtras().getString("movie_id");

        String movie_id = "281957";

        String builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon() + movie_id + "/reviews?&api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY;

        String url = builtUri.toString();

        return url;
    }


    /**
     * This parses the JSON for the trailers
     */
    public void parseTrailerJson() {

        client = new AsyncHttpClient();
        client.get(getActivity(), builtTrailerURL(), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                trailerResponseStr = new String(responseBody);
                gson = new Gson();
                videoResponseObj = gson.fromJson(trailerResponseStr, ResponseTrailer.class);
                trailerLVAdapter = new LVAdapterTrailer(getActivity(), videoResponseObj.getResults());
                trailerListView.setAdapter(trailerLVAdapter);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    /**
     * This parses the JSON data for the reviews
     */
    public void parseReviewJson() {

        client = new AsyncHttpClient();
        client.get(getActivity(), builtReviewURL(), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                reviewResponseStr = new String(responseBody);
                gson = new Gson();
                reviewResponseObj = gson.fromJson(reviewResponseStr, ResponseReview.class);
                reviewResponseObj.getResults();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    /**
     * @param movieId Takes in a movie_id to check if the movie already exists in the favorits table
     * @return returns true if movie id exists and false if movie id does not exist
     */
    public boolean checkForMovie(String movieId) {

        Cursor movieCursor = getContext().getContentResolver().query(
                MovieContract.FavoriteEntry.CONTENT_URI,
                null,
                "movie_id=?",
                new String[]{movieId},
                null
        );

        boolean existing = false;
        while (movieCursor.moveToNext()) {
            existing = true;
        }
        movieCursor.close();
        return existing;
    }


    //TODO:  You need to understand what this really does because it was taken from Sunshine

    /**
     * This was from Sunshine
     */
    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    /**
     * This was also from Sunshine
     */
    //TODO:  You need to understand what this does too because it was also taken from Sunshine
    public abstract static class PollingCheck {
        private static final long TIME_SLICE = 50;
        private long mTimeout = 3000;

        public PollingCheck() {
        }

        public PollingCheck(long timeout) {
            mTimeout = timeout;
        }

        protected abstract boolean check();

        public void run() {
            if (check()) {
                return;
            }

            long timeout = mTimeout;
            while (timeout > 0) {
                try {
                    Thread.sleep(TIME_SLICE);
                } catch (InterruptedException e) {
                    Assert.fail("unexpected InterruptedException");
                }

                if (check()) {
                    return;
                }

                timeout -= TIME_SLICE;
            }

            Assert.fail("unexpected timeout");
        }

        public void check(CharSequence message, long timeout, Callable<Boolean> condition)
                throws Exception {
            while (timeout > 0) {
                if (condition.call()) {
                    return;
                }

                Thread.sleep(TIME_SLICE);
                timeout -= TIME_SLICE;
            }

            Assert.fail(message.toString());
        }
    }


}

