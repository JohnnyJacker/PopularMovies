//package com.example.android.popularmovies.data;
//
//import android.content.ComponentName;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.pm.PackageManager;
//import android.content.pm.ProviderInfo;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Movie;
//import android.net.Uri;
//import android.test.AndroidTestCase;
//
///**
// * Created by T510 Owner on 4/2/2016.
// */
//public class TestProvider extends AndroidTestCase {
//
//    public static final String LOG_TAG = TestProvider.class.getSimpleName();
//
//    /*
//       This helper function deletes all records from both database tables using the ContentProvider.
//       It also queries the ContentProvider to make sure that the database has been successfully
//       deleted, so it cannot be used until the Query and Delete functions have been written
//       in the ContentProvider.
//       Students: Replace the calls to deleteAllRecordsFromDB with this one after you have written
//       the delete functionality in the ContentProvider.
//     */
//    public void deleteAllRecordsFromProvider() {
//        mContext.getContentResolver().delete(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null
//        );
//        mContext.getContentResolver().delete(
//                MovieContract.TrailerEntry.CONTENT_URI,
//                null,
//                null
//        );
//        mContext.getContentResolver().delete(
//                MovieContract.ReviewEntry.CONTENT_URI,
//                null,
//                null
//        );
//        mContext.getContentResolver().delete(
//                MovieContract.FavoriteEntry.CONTENT_URI,
//                null,
//                null
//        );
//
//        Cursor cursor = mContext.getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Movie table during delete", 0, cursor.getCount());
//        cursor.close();
//
//        cursor = mContext.getContentResolver().query(
//                MovieContract.TrailerEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Trailer table during delete", 0, cursor.getCount());
//        cursor.close();
//
//        cursor = mContext.getContentResolver().query(
//                MovieContract.ReviewEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Review table during delete", 0, cursor.getCount());
//        cursor.close();
//
//        cursor = mContext.getContentResolver().query(
//                MovieContract.FavoriteEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Favorite table during delete", 0, cursor.getCount());
//        cursor.close();
//    }
//
//    /*
//       This helper function deletes all records from both database tables using the database
//       functions only.  This is designed to be used to reset the state of the database until the
//       delete functionality is available in the ContentProvider.
//     */
////    public void deleteAllRecordsFromDB() {
////        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
////        SQLiteDatabase db = dbHelper.getWritableDatabase();
////
////        db.delete(MovieContract.MovieEntry.TABLE_NAME, null, null);
////        db.delete(MovieContract.TrailerEntry.TABLE_NAME, null, null);
////        db.delete(MovieContract.ReviewEntry.TABLE_NAME, null, null);
////        db.delete(MovieContract.FavoriteEntry.TABLE_NAME, null, null);
////        db.close();
////    }
//
//    /*
//        Student: Refactor this function to use the deleteAllRecordsFromProvider functionality once
//        you have implemented delete functionality there.
//     */
////    public void deleteAllRecords() {
////        deleteAllRecordsFromDB();
////    }
//
//    // Since we want each test to start with a clean slate, run deleteAllRecords
//    // in setUp (called by the test runner before each test).
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
////        deleteAllRecords();
//    }
//
//    /*
//        This test checks to make sure that the content provider is registered correctly.
//        Students: Uncomment this test to make sure you've correctly registered the WeatherProvider.
//     */
//    public void testProviderRegistry() {
//        PackageManager pm = mContext.getPackageManager();
//
//        // We define the component name based on the package name from the context and the
//        // WeatherProvider class.
//        ComponentName componentName = new ComponentName(mContext.getPackageName(),
//                MovieProvider.class.getName());
//        try {
//            // Fetch the provider info using the component name from the PackageManager
//            // This throws an exception if the provider isn't registered.
//            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
//
//            // Make sure that the registered authority matches the authority from the Contract.
//            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
//                            " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
//                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
//        } catch (PackageManager.NameNotFoundException e) {
//            // I guess the provider isn't registered correctly.
//            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(),
//                    false);
//        }
//    }
//
//    /*
//            This test doesn't touch the database.  It verifies that the ContentProvider returns
//            the correct type for each type of URI that it can handle.
//            Students: Uncomment this test to verify that your implementation of GetType is
//            functioning correctly.
//         */
//    public void testGetType() {
//        // content://com.example.android.popularmovies.app/movie/
//        String movieType = mContext.getContentResolver().getType(MovieContract.MovieEntry.CONTENT_URI);
//        // vnd.android.cursor.dir/com.example.android.popularmovies.app/movie
//        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
//                MovieContract.MovieEntry.CONTENT_TYPE, movieType);
//
//        long testMovieId = 940741;
//        // content://com.example.android.popularmovies.app/movie/940741
//        movieType = mContext.getContentResolver().getType(
//                MovieContract.MovieEntry.buildMovieUri(testMovieId));
//        // vnd.android.cursor.dir/com.example.android.popularmovies.app/movie
//        assertEquals("Error: the MovieEntry CONTENT_URI with movie_id should return MovieEntry.CONTENT_TYPE",
//                MovieContract.MovieEntry.CONTENT_ITEM_TYPE, movieType);
//
//        // content://com.example.android.popularmovies.app/trailer/
//        String trailerType = mContext.getContentResolver().getType(MovieContract.TrailerEntry.CONTENT_URI);
//        // vnd.android.cursor.dir/com.example.android.popularmovies.app/trailer
//        assertEquals("Error: the TrailerEntry CONTENT_URI should return TrailerEntry.CONTENT_TYPE",
//                MovieContract.TrailerEntry.CONTENT_TYPE, trailerType);
//
//        long testTrailerId = 940741;
//        // content://com.example.android.popularmovies.app/trailer/940741
//        trailerType = mContext.getContentResolver().getType(
//                MovieContract.TrailerEntry.buildTrailerUri(testTrailerId));
//        // vnd.android.cursor.dir/com.example.android.popularmovies.app/trailer
//        assertEquals("Error: the TrailerEntry CONTENT_URI with movie_id should return TrailerEntry.CONTENT_TYPE",
//                MovieContract.TrailerEntry.CONTENT_ITEM_TYPE, trailerType);
//
//
//        // content://com.example.android.popularmovies.app/review/
//        String reviewType = mContext.getContentResolver().getType(MovieContract.ReviewEntry.CONTENT_URI);
//        // vnd.android.cursor.dir/com.example.android.popularmovies.app/review
//        assertEquals("Error: the ReviewEntry CONTENT_URI should return ReviewEntry.CONTENT_TYPE",
//                MovieContract.ReviewEntry.CONTENT_TYPE, reviewType);
//
//        long testReviewId = 940741;
//        // content://com.example.android.popularmovies.app/review/940741
//        reviewType = mContext.getContentResolver().getType(
//                MovieContract.ReviewEntry.buildReviewUri(testReviewId));
//        // vnd.android.cursor.dir/com.example.android.popularmovies.app/review
//        assertEquals("Error: the ReviewEntry CONTENT_URI with movie_id should return ReviewEntry.CONTENT_TYPE",
//                MovieContract.ReviewEntry.CONTENT_ITEM_TYPE, reviewType);
//
//
//
//        // content://com.example.android.popularmovies.app/favorite/
//        String favoriteType = mContext.getContentResolver().getType(MovieContract.FavoriteEntry.CONTENT_URI);
//        // vnd.android.cursor.dir/com.example.android.popularmovies.app/favorite
//        assertEquals("Error: the FavoriteEntry CONTENT_URI should return FavoriteEntry.CONTENT_TYPE",
//                MovieContract.FavoriteEntry.CONTENT_TYPE, favoriteType);
//
//        long testFavoriteId = 940741;
//        // content://com.example.android.popularmovies.app/favorite/940741
//        favoriteType = mContext.getContentResolver().getType(
//                MovieContract.FavoriteEntry.buildFavoriteUri(testFavoriteId));
//        // vnd.android.cursor.dir/com.example.android.popularmovies.app/favorite
//        assertEquals("Error: the FavoriteEntry CONTENT_URI with movie_id should return FavoriteEntry.CONTENT_TYPE",
//                MovieContract.FavoriteEntry.CONTENT_ITEM_TYPE, favoriteType);
//
//    }
//
//
//    /*
//        This test uses the database directly to insert and then uses the ContentProvider to
//        read out the data.  Uncomment this test to see if the basic weather query functionality
//        given in the ContentProvider is working correctly.
//     */
//    public void testBasicMovieQuery() {
//        // insert our test records into the database
//        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues testValues = TestUtilities.createMovieValues();
//
//
//        long movieRowId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, testValues);
//        assertTrue("Unable to Insert MovieEntry into the Database", movieRowId != -1);
//
//        db.close();
//
//        // Test the basic content provider query
//        Cursor movieCursor = mContext.getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Make sure we get the correct cursor out of the database
//        TestUtilities.validateCursor("testBasicMovieQuery", movieCursor, testValues);
//    }
//
//    public void testBasicTrailerQuery() {
//        // insert our test records into the database
//        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues testValues = TestUtilities.createTrailerValues();
//
//
//        long trailerRowId = db.insert(MovieContract.TrailerEntry.TABLE_NAME, null, testValues);
//        assertTrue("Unable to Insert TrailerEntry into the Database", trailerRowId != -1);
//
//        db.close();
//
//        // Test the basic content provider query
//        Cursor trailerCursor = mContext.getContentResolver().query(
//                MovieContract.TrailerEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Make sure we get the correct cursor out of the database
//        TestUtilities.validateCursor("testBasicTrailerQuery", trailerCursor, testValues);
//    }
//
//    public void testBasicReviewQuery() {
//        // insert our test records into the database
//        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues testValues = TestUtilities.createReviewValues();
//
//
//        long reviewRowId = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, testValues);
//        assertTrue("Unable to Insert ReviewEntry into the Database", reviewRowId != -1);
//
//        db.close();
//
//        // Test the basic content provider query
//        Cursor reviewCursor = mContext.getContentResolver().query(
//                MovieContract.ReviewEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Make sure we get the correct cursor out of the database
//        TestUtilities.validateCursor("testBasicReviewQuery", reviewCursor, testValues);
//    }
//
//    public void testBasicFavoriteQuery() {
//        // insert our test records into the database
//        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues testValues = TestUtilities.createFavoriteValues();
//
//
//        long favoriteRowId = db.insert(MovieContract.FavoriteEntry.TABLE_NAME, null, testValues);
//        assertTrue("Unable to Insert FavoriteEntry into the Database", favoriteRowId != -1);
//
//        db.close();
//
//        // Test the basic content provider query
//        Cursor favoriteCursor = mContext.getContentResolver().query(
//                MovieContract.FavoriteEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Make sure we get the correct cursor out of the database
//        TestUtilities.validateCursor("testBasicFavoriteQuery", favoriteCursor, testValues);
//    }
//
//    /*
//        This test uses the database directly to insert and then uses the ContentProvider to
//        read out the data.  Uncomment this test to see if your location queries are
//        performing correctly.
//     */
////    public void testBasicLocationQueries() {
////        // insert our test records into the database
////        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
////        SQLiteDatabase db = dbHelper.getWritableDatabase();
////
////        ContentValues testValues = TestUtilities.createNorthPoleLocationValues();
////        long locationRowId = TestUtilities.insertNorthPoleLocationValues(mContext);
////
////        // Test the basic content provider query
////        Cursor locationCursor = mContext.getContentResolver().query(
////                LocationEntry.CONTENT_URI,
////                null,
////                null,
////                null,
////                null
////        );
////
////        // Make sure we get the correct cursor out of the database
////        TestUtilities.validateCursor("testBasicLocationQueries, location query", locationCursor, testValues);
////
////        // Has the NotificationUri been set correctly? --- we can only test this easily against API
////        // level 19 or greater because getNotificationUri was added in API level 19.
////        if ( Build.VERSION.SDK_INT >= 19 ) {
////            assertEquals("Error: Location Query did not properly set NotificationUri",
////                    locationCursor.getNotificationUri(), LocationEntry.CONTENT_URI);
////        }
////    }
//
//    /*
//        This test uses the provider to insert and then update the data. Uncomment this test to
//        see if your update location is functioning correctly.
//     */
////    public void testUpdateLocation() {
////        // Create a new map of values, where column names are the keys
////        ContentValues values = TestUtilities.createNorthPoleLocationValues();
////
////        Uri locationUri = mContext.getContentResolver().
////                insert(LocationEntry.CONTENT_URI, values);
////        long locationRowId = ContentUris.parseId(locationUri);
////
////        // Verify we got a row back.
////        assertTrue(locationRowId != -1);
////        Log.d(LOG_TAG, "New row id: " + locationRowId);
////
////        ContentValues updatedValues = new ContentValues(values);
////        updatedValues.put(LocationEntry._ID, locationRowId);
////        updatedValues.put(LocationEntry.COLUMN_CITY_NAME, "Santa's Village");
////
////        // Create a cursor with observer to make sure that the content provider is notifying
////        // the observers as expected
////        Cursor locationCursor = mContext.getContentResolver().query(LocationEntry.CONTENT_URI, null, null, null, null);
////
////        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
////        locationCursor.registerContentObserver(tco);
////
////        int count = mContext.getContentResolver().update(
////                LocationEntry.CONTENT_URI, updatedValues, LocationEntry._ID + "= ?",
////                new String[] { Long.toString(locationRowId)});
////        assertEquals(count, 1);
////
////        // Test to make sure our observer is called.  If not, we throw an assertion.
////        //
////        // Students: If your code is failing here, it means that your content provider
////        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
////        tco.waitForNotificationOrFail();
////
////        locationCursor.unregisterContentObserver(tco);
////        locationCursor.close();
////
////        // A cursor is your primary interface to the query results.
////        Cursor cursor = mContext.getContentResolver().query(
////                LocationEntry.CONTENT_URI,
////                null,   // projection
////                LocationEntry._ID + " = " + locationRowId,
////                null,   // Values for the "where" clause
////                null    // sort order
////        );
////
////        TestUtilities.validateCursor("testUpdateLocation.  Error validating location entry update.",
////                cursor, updatedValues);
////
////        cursor.close();
////    }
//
//
//    // Make sure we can still delete after adding/updating stuff
//    //
//    // Student: Uncomment this test after you have completed writing the insert functionality
//    // in your provider.  It relies on insertions with testInsertReadProvider, so insert and
//    // query functionality must also be complete before this test can be used.
////    public void testInsertReadProvider() {
////        ContentValues testMovieValues = TestUtilities.createMovieValues();
//////        ContentValues testTrailerValues = TestUtilities.createTrailerValues();
//////        ContentValues testReviewValues = TestUtilities.createReviewValues();
//////        ContentValues testFavoriteValues = TestUtilities.createFavoriteValues();
////
////        // Register a content observer for our insert.  This time, directly with the content resolver
////        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
////        mContext.getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, tco);
//////        mContext.getContentResolver().registerContentObserver(MovieContract.TrailerEntry.CONTENT_URI, true, tco);
//////        mContext.getContentResolver().registerContentObserver(MovieContract.ReviewEntry.CONTENT_URI, true, tco);
//////        mContext.getContentResolver().registerContentObserver(MovieContract.FavoriteEntry.CONTENT_URI, true, tco);
////
////
////        Uri movieUri = mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, testMovieValues);
//////        Uri trailerUri = mContext.getContentResolver().insert(MovieContract.TrailerEntry.CONTENT_URI, testTrailerValues);
//////        Uri reviewUri = mContext.getContentResolver().insert(MovieContract.ReviewEntry.CONTENT_URI, testReviewValues);
//////        Uri favoriteUri = mContext.getContentResolver().insert(MovieContract.FavoriteEntry.CONTENT_URI, testFavoriteValues);
////
////
////        // Did our content observer get called?  Students:  If this fails, your insert location
////        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
////        tco.waitForNotificationOrFail();
////        mContext.getContentResolver().unregisterContentObserver(tco);
////
////        long movieRowId = ContentUris.parseId(movieUri);
//////        long trailerRowId = ContentUris.parseId(trailerUri);
//////        long reviewRowId = ContentUris.parseId(reviewUri);
//////        long favoriteRowId = ContentUris.parseId(favoriteUri);
////
////        // Verify we got a row back.
////        assertTrue(movieRowId != -1);
//////        assertTrue(trailerRowId != -1);
//////        assertTrue(reviewRowId != -1);
//////        assertTrue(favoriteRowId != -1);
////
////        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
////        // the round trip.
////
////        // A cursor is your primary interface to the query results.
////        Cursor cursor = mContext.getContentResolver().query(
////                MovieContract.MovieEntry.CONTENT_URI,
////                null, // leaving "columns" null just returns all the columns.
////                null, // cols for "where" clause
////                null, // values for "where" clause
////                null  // sort order
////        );
////
////        TestUtilities.validateCursor("testInsertReadProvider. Error validating MovieEntry.",
////                cursor, testMovieValues);
////
////
////        ContentValues movieValues = TestUtilities.createMovieValues();
////        // The TestContentObserver is a one-shot class
////        tco = TestUtilities.getTestContentObserver();
////
////        mContext.getContentResolver().registerContentObserver(MovieContract.MovieEntry.CONTENT_URI, true, tco);
////
////        Uri movieInsertUri = mContext.getContentResolver()
////                .insert(MovieContract.MovieEntry.CONTENT_URI, movieValues);
////        assertTrue(movieInsertUri != null);
////
////        // Did our content observer get called?  Students:  If this fails, your insert weather
////        // in your ContentProvider isn't calling
////        // getContext().getContentResolver().notifyChange(uri, null);
////        tco.waitForNotificationOrFail();
////        mContext.getContentResolver().unregisterContentObserver(tco);
////
////        // A cursor is your primary interface to the query results.
////        Cursor movieCursor = mContext.getContentResolver().query(
////                MovieContract.MovieEntry.CONTENT_URI,  // Table to Query
////                null, // leaving "columns" null just returns all the columns.
////                null, // cols for "where" clause
////                null, // values for "where" clause
////                null // columns to group by
////        );
////
////        TestUtilities.validateCursor("testInsertReadProvider. Error validating WeatherEntry insert.",
////                movieCursor, movieValues);
////
////        // Add the location values in with the weather data so that we can make
////        // sure that the join worked and we actually get all the values back
////        movieValues.putAll(testMovieValues);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////        // A cursor is your primary interface to the query results.
////        Cursor cTrailer = mContext.getContentResolver().query(
////                MovieContract.TrailerEntry.CONTENT_URI,
////                null, // leaving "columns" null just returns all the columns.
////                null, // cols for "where" clause
////                null, // values for "where" clause
////                null  // sort order
////        );
////
////        TestUtilities.validateCursor("testInsertReadProvider. Error validating TrailerEntry.",
////                cTrailer, testTrailerValues);
////
////
////        ContentValues trailerValues = TestUtilities.createTrailerValues();
////        // The TestContentObserver is a one-shot class
////        tco = TestUtilities.getTestContentObserver();
////
////        mContext.getContentResolver().registerContentObserver(MovieContract.TrailerEntry.CONTENT_URI, true, tco);
////
////        Uri trailerInsertUri = mContext.getContentResolver()
////                .insert(MovieContract.TrailerEntry.CONTENT_URI, trailerValues);
////        assertTrue(trailerInsertUri != null);
////
////        // Did our content observer get called?  Students:  If this fails, your insert weather
////        // in your ContentProvider isn't calling
////        // getContext().getContentResolver().notifyChange(uri, null);
////        tco.waitForNotificationOrFail();
////        mContext.getContentResolver().unregisterContentObserver(tco);
////
////        // A cursor is your primary interface to the query results.
////        Cursor trailerCursor = mContext.getContentResolver().query(
////                MovieContract.TrailerEntry.CONTENT_URI,  // Table to Query
////                null, // leaving "columns" null just returns all the columns.
////                null, // cols for "where" clause
////                null, // values for "where" clause
////                null // columns to group by
////        );
////
////        TestUtilities.validateCursor("testInsertReadProvider. Error validating TrailerEntry insert.",
////                trailerCursor, trailerValues);
////
////        // Add the location values in with the weather data so that we can make
////        // sure that the join worked and we actually get all the values back
////        trailerValues.putAll(testTrailerValues);
//
//
//
//
//
//
//
////        // Get the joined Trailer data
////        trailerCursor = mContext.getContentResolver().query(
////                MovieContract.TrailerEntry.buildTrailerUri(385497),
////                null, // leaving "columns" null just returns all the columns.
////                null, // cols for "where" clause
////                null, // values for "where" clause
////                null  // sort order
////        );
////        TestUtilities.validateCursor("testInsertReadProvider.  Error validating joined Weather and Location Data.",
////                trailerCursor, weatherValues);
////
////        // Get the joined Weather and Location data with a start date
////        weatherCursor = mContext.getContentResolver().query(
////                WeatherEntry.buildWeatherLocationWithStartDate(
////                        TestUtilities.TEST_LOCATION, TestUtilities.TEST_DATE),
////                null, // leaving "columns" null just returns all the columns.
////                null, // cols for "where" clause
////                null, // values for "where" clause
////                null  // sort order
////        );
////        TestUtilities.validateCursor("testInsertReadProvider.  Error validating joined Weather and Location Data with start date.",
////                weatherCursor, weatherValues);
////
////        // Get the joined Weather data for a specific date
////        weatherCursor = mContext.getContentResolver().query(
////                WeatherEntry.buildWeatherLocationWithDate(TestUtilities.TEST_LOCATION, TestUtilities.TEST_DATE),
////                null,
////                null,
////                null,
////                null
////        );
////        TestUtilities.validateCursor("testInsertReadProvider.  Error validating joined Weather and Location data for a specific date.",
////                weatherCursor, weatherValues);
////    }
//
//    // Make sure we can still delete after adding/updating stuff
//    //
//    // Student: Uncomment this test after you have completed writing the delete functionality
//    // in your provider.  It relies on insertions with testInsertReadProvider, so insert and
//    // query functionality must also be complete before this test can be used.
////    public void testDeleteRecords() {
////        testInsertReadProvider();
////
////        // Register a content observer for our location delete.
////        TestUtilities.TestContentObserver locationObserver = TestUtilities.getTestContentObserver();
////        mContext.getContentResolver().registerContentObserver(LocationEntry.CONTENT_URI, true, locationObserver);
////
////        // Register a content observer for our weather delete.
////        TestUtilities.TestContentObserver weatherObserver = TestUtilities.getTestContentObserver();
////        mContext.getContentResolver().registerContentObserver(WeatherEntry.CONTENT_URI, true, weatherObserver);
////
////        deleteAllRecordsFromProvider();
////
////        // Students: If either of these fail, you most-likely are not calling the
////        // getContext().getContentResolver().notifyChange(uri, null); in the ContentProvider
////        // delete.  (only if the insertReadProvider is succeeding)
////        locationObserver.waitForNotificationOrFail();
////        weatherObserver.waitForNotificationOrFail();
////
////        mContext.getContentResolver().unregisterContentObserver(locationObserver);
////        mContext.getContentResolver().unregisterContentObserver(weatherObserver);
////    }
//
//
////    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;
////    static ContentValues[] createBulkInsertWeatherValues(long locationRowId) {
////        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];
////
////        for ( int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, currentTestDate+= millisecondsInADay ) {
////            ContentValues weatherValues = new ContentValues();
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_LOC_KEY, locationRowId);
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, currentTestDate);
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DEGREES, 1.1);
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, 1.2 + 0.01 * (float) i);
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, 1.3 - 0.01 * (float) i);
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, 75 + i);
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, 65 - i);
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, "Asteroids");
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, 5.5 + 0.2 * (float) i);
////            weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, 321);
////            returnContentValues[i] = weatherValues;
////        }
////        return returnContentValues;
////    }
//
//    // Student: Uncomment this test after you have completed writing the BulkInsert functionality
//    // in your provider.  Note that this test will work with the built-in (default) provider
//    // implementation, which just inserts records one-at-a-time, so really do implement the
//    // BulkInsert ContentProvider function.
////    public void testBulkInsert() {
////        // first, let's create a location value
////        ContentValues testValues = TestUtilities.createNorthPoleLocationValues();
////        Uri locationUri = mContext.getContentResolver().insert(LocationEntry.CONTENT_URI, testValues);
////        long locationRowId = ContentUris.parseId(locationUri);
////
////        // Verify we got a row back.
////        assertTrue(locationRowId != -1);
////
////        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
////        // the round trip.
////
////        // A cursor is your primary interface to the query results.
////        Cursor cursor = mContext.getContentResolver().query(
////                LocationEntry.CONTENT_URI,
////                null, // leaving "columns" null just returns all the columns.
////                null, // cols for "where" clause
////                null, // values for "where" clause
////                null  // sort order
////        );
////
////        TestUtilities.validateCursor("testBulkInsert. Error validating LocationEntry.",
////                cursor, testValues);
////
////        // Now we can bulkInsert some weather.  In fact, we only implement BulkInsert for weather
////        // entries.  With ContentProviders, you really only have to implement the features you
////        // use, after all.
////        ContentValues[] bulkInsertContentValues = createBulkInsertWeatherValues(locationRowId);
////
////        // Register a content observer for our bulk insert.
////        TestUtilities.TestContentObserver weatherObserver = TestUtilities.getTestContentObserver();
////        mContext.getContentResolver().registerContentObserver(WeatherEntry.CONTENT_URI, true, weatherObserver);
////
////        int insertCount = mContext.getContentResolver().bulkInsert(WeatherEntry.CONTENT_URI, bulkInsertContentValues);
////
////        // Students:  If this fails, it means that you most-likely are not calling the
////        // getContext().getContentResolver().notifyChange(uri, null); in your BulkInsert
////        // ContentProvider method.
////        weatherObserver.waitForNotificationOrFail();
////        mContext.getContentResolver().unregisterContentObserver(weatherObserver);
////
////        assertEquals(insertCount, BULK_INSERT_RECORDS_TO_INSERT);
////
////        // A cursor is your primary interface to the query results.
////        cursor = mContext.getContentResolver().query(
////                WeatherEntry.CONTENT_URI,
////                null, // leaving "columns" null just returns all the columns.
////                null, // cols for "where" clause
////                null, // values for "where" clause
////                WeatherEntry.COLUMN_DATE + " ASC"  // sort order == by DATE ASCENDING
////        );
////
////        // we should have as many records in the database as we've inserted
////        assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);
////
////        // and let's make sure they match the ones we created
////        cursor.moveToFirst();
////        for ( int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext() ) {
////            TestUtilities.validateCurrentRecord("testBulkInsert.  Error validating WeatherEntry " + i,
////                    cursor, bulkInsertContentValues[i]);
////        }
////        cursor.close();
////    }
//}
