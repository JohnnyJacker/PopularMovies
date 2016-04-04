package com.example.android.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieDbHelper;

import junit.framework.Assert;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class MainActivity extends AppCompatActivity {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Getting reference to writable database
//        MovieDbHelper dbHelper = new MovieDbHelper(this);
//        dbHelper.getReadableDatabase();

//        testInsertMovie();
//        testInsertTrailer();
//        testInsertReview();
//        testInsertFavorite();
//        testCursorTables();

    }

//    public void testUpdateRecords() {
//
//        //        // Test out updating the tables
//        ContentValues movieUpdateValues = createMovieValues();
//
//        Uri movieUpdateUri = this.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movieUpdateValues);
//
//       long movieUpdateRowId = ContentUris.parseId(movieUpdateUri);
//
//        assertTrue(movieUpdateRowId != -1);
//        Log.d(LOG_TAG, "New row id: " + movieUpdateRowId);
//
//        ContentValues updatedMovieValues = new ContentValues(movieUpdateValues);
//        updatedMovieValues.put(MovieContract.MovieEntry._ID, movieUpdateRowId);
//        updatedMovieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "The Revenant");
//        updatedMovieValues.put(MovieContract.MovieEntry.COLUMN_IMAGE_PATH, "This is supposed to be an image path");
//        updatedMovieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "This is the new overview");
//        updatedMovieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE, "New Release Date");
//        updatedMovieValues.put(MovieContract.MovieEntry.COLUMN_RATING, "New Rating");
//        updatedMovieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, "New Movie ID");
//
//        Cursor movieUpdateCursor = this.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
//                null, null, null, null);
//
//        TestContentObserver updateMovieTco = getTestContentObserver();
//        movieUpdateCursor.registerContentObserver(updateMovieTco);
//
//        int count = this.getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI, updatedMovieValues,
//                MovieContract.MovieEntry._ID + "= ?", new String[] {Long.toString(movieUpdateRowId)});
//
//        assertEquals(count, 1);
//
//        updateMovieTco.waitForNotificationOrFail();
//        movieUpdateCursor.unregisterContentObserver(updateMovieTco);
//        movieUpdateCursor.close();
//
//        Cursor cursor = this.getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                MovieContract.MovieEntry._ID + " = " + movieUpdateRowId,
//                null,
//                null
//        );
//
//        validateCursor("testUpdateMovie.  Error validating movie entry update", cursor, updatedMovieValues);
//
//        cursor.close();
//        // End of updating the movie database test
//
//
//        // Test the basic content provider query for the movie table
//        Cursor movieTestCursor = this.getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        Log.d("Testing Movie Entry", DatabaseUtils.dumpCursorToString(movieTestCursor));
//
//    }

//    public void testDeleteRecords() {
//
//        TestContentObserver movieObserver = getTestContentObserver();
//        this.getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, movieObserver);
//
//        TestContentObserver trailerObserver = getTestContentObserver();
//        this.getContentResolver().registerContentObserver(MovieContract.TrailerEntry.CONTENT_URI, true, trailerObserver);
//
//        TestContentObserver reviewObserver = getTestContentObserver();
//        this.getContentResolver().registerContentObserver(MovieContract.ReviewEntry.CONTENT_URI, true, reviewObserver);
//
//        TestContentObserver favoriteObserver = getTestContentObserver();
//        this.getContentResolver().registerContentObserver(MovieContract.FavoriteEntry.CONTENT_URI, true, favoriteObserver);
//
//        deleteAllRecordsFromProvider();
//
//        movieObserver.waitForNotificationOrFail();
//        trailerObserver.waitForNotificationOrFail();
//        reviewObserver.waitForNotificationOrFail();
//        favoriteObserver.waitForNotificationOrFail();
//
//        this.getContentResolver().unregisterContentObserver(movieObserver);
//        this.getContentResolver().unregisterContentObserver(trailerObserver);
//        this.getContentResolver().unregisterContentObserver(reviewObserver);
//        this.getContentResolver().unregisterContentObserver(favoriteObserver);
//    }

//    public void testInsertMovie() {
//
//        //  Testing inserting data into the movie table
//        ContentValues testMovieValues = createMovieValues();
//
//        TestContentObserver movieTco = getTestContentObserver();
//        this.getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, movieTco);
//
//
//        Uri movieUri = this.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, testMovieValues);
//
//        movieTco.waitForNotificationOrFail();
//        this.getContentResolver().unregisterContentObserver(movieTco);
//
//        long movieRowId = ContentUris.parseId(movieUri);
//
//        assertTrue(movieRowId != -1);
//
//        Cursor cursorMovie = this.getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null  // sort order
//        );
//
//        validateCursor("testInsertReadProvider. Error validating MovieEntry.",
//                cursorMovie, testMovieValues);
//
//        testMovieValues.putAll(testMovieValues);
//        // End of inserting data into the movie table
//
//    }

//    public void testInsertTrailer() {
//        // Testing inserting data into the trailer table
//        ContentValues testTrailerValues = createTrailerValues();
//
//        TestContentObserver trailerTco = getTestContentObserver();
//        this.getContentResolver().registerContentObserver(MovieContract.TrailerEntry.CONTENT_URI, true, trailerTco);
//
//
//        Uri trailerUri = this.getContentResolver().insert(MovieContract.TrailerEntry.CONTENT_URI, testTrailerValues);
//
//        trailerTco.waitForNotificationOrFail();
//        this.getContentResolver().unregisterContentObserver(trailerTco);
//
//        long trailerRowId = ContentUris.parseId(trailerUri);
//
//        assertTrue(trailerRowId != -1);
//
//        Cursor cursorTrailer = this.getContentResolver().query(
//                MovieContract.TrailerEntry.CONTENT_URI,
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null  // sort order
//        );
//
//        validateCursor("testInsertReadProvider. Error validating TrailerEntry.",
//                cursorTrailer, testTrailerValues);
//
//        testTrailerValues.putAll(testTrailerValues);
//        // End of inserting data into the trailer table
//    }
//    public void testInsertReview() {
//        // Testing inserting data into the review table
//        ContentValues testReviewValues = createReviewValues();
//
//        TestContentObserver reviewTco = getTestContentObserver();
//        this.getContentResolver().registerContentObserver(MovieContract.ReviewEntry.CONTENT_URI, true, reviewTco);
//
//
//        Uri reviewUri = this.getContentResolver().insert(MovieContract.ReviewEntry.CONTENT_URI, testReviewValues);
//
//        reviewTco.waitForNotificationOrFail();
//        this.getContentResolver().unregisterContentObserver(reviewTco);
//
//        long reviewRowId = ContentUris.parseId(reviewUri);
//
//        assertTrue(reviewRowId != -1);
//
//        Cursor cursorReview = this.getContentResolver().query(
//                MovieContract.ReviewEntry.CONTENT_URI,
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null  // sort order
//        );
//
//        validateCursor("testInsertReadProvider. Error validating ReviewEntry.",
//                cursorReview, testReviewValues);
//
//        testReviewValues.putAll(testReviewValues);
//        // End of inserting data into the Review table
//    }
//    public void testInsertFavorite() {
//        // Testing inserting data into the favorite table
//        ContentValues testFavoriteValues = createFavoriteValues();
//
//        TestContentObserver favoriteTco = getTestContentObserver();
//        this.getContentResolver().registerContentObserver(MovieContract.FavoriteEntry.CONTENT_URI, true, favoriteTco);
//
//
//        Uri favoriteUri = this.getContentResolver().insert(MovieContract.FavoriteEntry.CONTENT_URI, testFavoriteValues);
//
//        favoriteTco.waitForNotificationOrFail();
//        this.getContentResolver().unregisterContentObserver(favoriteTco);
//
//        long favoriteRowId = ContentUris.parseId(favoriteUri);
//
//        assertTrue(favoriteRowId != -1);
//
//        Cursor cursorFavorite = this.getContentResolver().query(
//                MovieContract.FavoriteEntry.CONTENT_URI,
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null  // sort order
//        );
//
//        validateCursor("testInsertReadProvider. Error validating FavoriteEntry.",
//                cursorFavorite, testFavoriteValues);
//
//        testFavoriteValues.putAll(testFavoriteValues);
//        // End of inserting data into the favorite table
//    }

//    public void testCursorTables() {
//
//        // Test the basic content provider query for the movie table
//        Cursor movieCursor = this.getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Test the basic content provider query for the trailer table
//        Cursor trailerCursor = this.getContentResolver().query(
//                MovieContract.TrailerEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Test the basic content provider query for the review table
//        Cursor reviewCursor = this.getContentResolver().query(
//                MovieContract.ReviewEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Test the basic content provider query for the favorite table
//        Cursor favoriteCursor = this.getContentResolver().query(
//                MovieContract.FavoriteEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        Log.d("Testing Movie Entry", DatabaseUtils.dumpCursorToString(movieCursor));
//        Log.d("Testing Trailer Entry", DatabaseUtils.dumpCursorToString(trailerCursor));
//        Log.d("Testing Review Entry", DatabaseUtils.dumpCursorToString(reviewCursor));
//        Log.d("Testing Favorite Entry", DatabaseUtils.dumpCursorToString(favoriteCursor));
//
//    }
//
//
//    public void deleteAllRecordsFromProvider() {
//        this.getContentResolver().delete(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null
//        );
//        this.getContentResolver().delete(
//                MovieContract.TrailerEntry.CONTENT_URI,
//                null,
//                null
//        );
//        this.getContentResolver().delete(
//                MovieContract.ReviewEntry.CONTENT_URI,
//                null,
//                null
//        );
//        this.getContentResolver().delete(
//                MovieContract.FavoriteEntry.CONTENT_URI,
//                null,
//                null
//        );
//
//        Cursor cursor = this.getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Movie table during delete", 0, cursor.getCount());
//        cursor.close();
//
//        cursor = this.getContentResolver().query(
//                MovieContract.TrailerEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Trailer table during delete", 0, cursor.getCount());
//        cursor.close();
//
//        cursor = this.getContentResolver().query(
//                MovieContract.ReviewEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Review table during delete", 0, cursor.getCount());
//        cursor.close();
//
//        cursor = this.getContentResolver().query(
//                MovieContract.FavoriteEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Favorite table during delete", 0, cursor.getCount());
//        cursor.close();
//    }

//    static class TestContentObserver extends ContentObserver {
//        final HandlerThread mHT;
//        boolean mContentChanged;
//
//        static TestContentObserver getTestContentObserver() {
//            HandlerThread ht = new HandlerThread("ContentObserverThread");
//            ht.start();
//            return new TestContentObserver(ht);
//        }
//
//        private TestContentObserver(HandlerThread ht) {
//            super(new Handler(ht.getLooper()));
//            mHT = ht;
//        }
//
//        // On earlier versions of Android, this onChange method is called
//        @Override
//        public void onChange(boolean selfChange) {
//            onChange(selfChange, null);
//        }
//
//        @Override
//        public void onChange(boolean selfChange, Uri uri) {
//            mContentChanged = true;
//        }
//
//        public void waitForNotificationOrFail() {
//            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
//            // It's useful to look at the Android CTS source for ideas on how to test your Android
//            // applications.  The reason that PollingCheck works is that, by default, the JUnit
//            // testing framework is not running on the main Android application thread.
//            new PollingCheck(5000) {
//                @Override
//                protected boolean check() {
//                    return mContentChanged;
//                }
//            }.run();
//            mHT.quit();
//        }
//    }
//
//    static TestContentObserver getTestContentObserver() {
//        return TestContentObserver.getTestContentObserver();
//    }
//
//    public abstract static class PollingCheck {
//        private static final long TIME_SLICE = 50;
//        private long mTimeout = 3000;
//
//        public PollingCheck() {
//        }
//
//        public PollingCheck(long timeout) {
//            mTimeout = timeout;
//        }
//
//        protected abstract boolean check();
//
//        public void run() {
//            if (check()) {
//                return;
//            }
//
//            long timeout = mTimeout;
//            while (timeout > 0) {
//                try {
//                    Thread.sleep(TIME_SLICE);
//                } catch (InterruptedException e) {
//                    Assert.fail("unexpected InterruptedException");
//                }
//
//                if (check()) {
//                    return;
//                }
//
//                timeout -= TIME_SLICE;
//            }
//
//            Assert.fail("unexpected timeout");
//        }
//
//        public void check(CharSequence message, long timeout, Callable<Boolean> condition)
//                throws Exception {
//            while (timeout > 0) {
//                if (condition.call()) {
//                    return;
//                }
//
//                Thread.sleep(TIME_SLICE);
//                timeout -= TIME_SLICE;
//            }
//
//            Assert.fail(message.toString());
//        }
//    }

//    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
//        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
//        validateCurrentRecord(error, valueCursor, expectedValues);
//        valueCursor.close();
//    }
//
//    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
//        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
//        for (Map.Entry<String, Object> entry : valueSet) {
//            String columnName = entry.getKey();
//            int idx = valueCursor.getColumnIndex(columnName);
//            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
//            String expectedValue = entry.getValue().toString();
//            assertEquals("Value '" + entry.getValue().toString() +
//                    "' did not match the expected value '" +
//                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
//        }
//    }

//    static ContentValues createMovieValues() {
//
//        ContentValues testMovieValues = new ContentValues();
//        testMovieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Casino Royale");
//        testMovieValues.put(MovieContract.MovieEntry.COLUMN_IMAGE_PATH, "https://jaysanalysis.files" +
//                ".wordpress.com/2013/06/casino_royale_poster_1.jpg");
//        testMovieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "This is one of my favorite films ever." +
//                "It has everything a proper James Bond film should have.  Right from the very beginning" +
//                "people are being chased and shot at in an adrenaline filled intro." +
//                "Bond song is best in history of bond movies.  Love it and highly recommend it to anybody that" +
//                "has not had the pleasure.");
//        testMovieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE, "2006");
//        testMovieValues.put(MovieContract.MovieEntry.COLUMN_RATING, "8/10");
//        testMovieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, "385497");
//
//
//        return testMovieValues;
//    }

//        static ContentValues createTrailerValues() {
//
//
//
//        ContentValues testTrailerValues = new ContentValues();
//
//        testTrailerValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, "385497");
//        testTrailerValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_KEY, "https://www.youtube.com/watch?v=xK7PbujRUOk");
//
//
//        return testTrailerValues;
//    }

//    static ContentValues createReviewValues() {
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

//    static ContentValues createFavoriteValues() {
//
//
//
//        ContentValues testFavoriteValues = new ContentValues();
//
//        testFavoriteValues.put(MovieContract.FavoriteEntry.COLUMN_MOVIE_ID, "385497");
//
//
//
//
//        return testFavoriteValues;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
