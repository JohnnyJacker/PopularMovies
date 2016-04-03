//package com.example.android.popularmovies.data;
//
//import android.content.ContentValues;
//import android.database.ContentObserver;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.test.AndroidTestCase;
//
//import com.example.android.popularmovies.utils.PollingCheck;
//
//import java.util.Map;
//import java.util.Set;
//
//public class TestUtilities extends AndroidTestCase {
//
//    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
//        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
//        validateCurrentRecord(error, valueCursor, expectedValues);
//        valueCursor.close();
//    }
//
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
//
//    static ContentValues createTrailerValues() {
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
//
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
//
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
//
//    /*
//    Students: The functions we provide inside of TestProvider use this utility class to test
//    the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
//    CTS tests.
//    Note that this only tests that the onChange function is called; it does not test that the
//    correct Uri is returned.
// */
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
//}
