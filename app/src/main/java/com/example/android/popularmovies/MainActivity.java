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
import android.widget.SimpleCursorAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MovieDbHelper dbHelper = new MovieDbHelper(this);
        dbHelper.getReadableDatabase();

        ContentValues testMovieValues = createMovieValues();

        TestContentObserver tco = getTestContentObserver();
        this.getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, tco);


        Uri movieUri = this.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, testMovieValues);

        tco.waitForNotificationOrFail();
        this.getContentResolver().unregisterContentObserver(tco);

        long movieRowId = ContentUris.parseId(movieUri);

        assertTrue(movieRowId != -1);

        Cursor cursor = this.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        validateCursor("testInsertReadProvider. Error validating MovieEntry.",
                cursor, testMovieValues);

        testMovieValues.putAll(testMovieValues);


        // Test the basic content provider query
        Cursor movieCursor = this.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
//
//        // Test the basic content provider query
//        Cursor trailerCursor = this.getContentResolver().query(
//                MovieContract.TrailerEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Test the basic content provider query
//        Cursor reviewCursor = this.getContentResolver().query(
//                MovieContract.ReviewEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Test the basic content provider query
//        Cursor favoriteCursor = this.getContentResolver().query(
//                MovieContract.FavoriteEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
        Log.d("Testing Movie Entry", DatabaseUtils.dumpCursorToString(movieCursor));
//        Log.d("Testing Trailer Entry", DatabaseUtils.dumpCursorToString(trailerCursor));
//        Log.d("Testing Review Entry", DatabaseUtils.dumpCursorToString(reviewCursor));
//        Log.d("Testing Favorite Entry", DatabaseUtils.dumpCursorToString(favoriteCursor));







    }

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

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createMovieValues() {

        ContentValues testMovieValues = new ContentValues();
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Casino Royale");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_IMAGE_PATH, "https://jaysanalysis.files" +
                ".wordpress.com/2013/06/casino_royale_poster_1.jpg");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "This is one of my favorite films ever." +
                "It has everything a proper James Bond film should have.  Right from the very beginning" +
                "people are being chased and shot at in an adrenaline filled intro." +
                "Bond song is best in history of bond movies.  Love it and highly recommend it to anybody that" +
                "has not had the pleasure.");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE, "2006");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_RATING, "8/10");
        testMovieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, "385497");


        return testMovieValues;
    }

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
