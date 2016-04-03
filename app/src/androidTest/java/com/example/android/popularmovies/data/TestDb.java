//package com.example.android.popularmovies.data;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.test.AndroidTestCase;
//
//import junit.framework.Test;
//
//import java.util.HashSet;
//
//
//public class TestDb extends AndroidTestCase {
//
//    // This is the Log Tag get used to creating one it's easier having it on deck
//    public static final String LOG_TAG = TestDb.class.getSimpleName();
//
//    // We want each test to start out with a clean slate
//    void deleteTheDatabase() {
//        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
//    }
//
//    /*
//    This function gets called before each test is executed to delete the database.  This makes
//    sure that we always have a clean test.
// */
//    public void setUp() {
//        deleteTheDatabase();
//    }
//
//    //  This method tests the creation of the database and columns
//    public void testCreateDb() throws Throwable {
//        // Build a HashSet of all of the table names we wish to look for
//        // Note that there will be another table in the DB that stores the
//        // Android metadata (db version information)
//        final HashSet<String> tableNameHashSet = new HashSet<>();
//        tableNameHashSet.add(MovieContract.MovieEntry.TABLE_NAME);
//        tableNameHashSet.add(MovieContract.TrailerEntry.TABLE_NAME);
//        tableNameHashSet.add(MovieContract.ReviewEntry.TABLE_NAME);
//        tableNameHashSet.add(MovieContract.FavoriteEntry.TABLE_NAME);
//
//        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
//        SQLiteDatabase db = new MovieDbHelper(this.mContext).getWritableDatabase();
//        assertEquals(true, db.isOpen());
//
//        // Have we created the tables we want?
//        Cursor cMovie = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
//        Cursor cTrailer = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
//        Cursor cReview = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
//        Cursor cFavorite = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
//
//        assertTrue("Error: This means that the database has not been created correctly",
//                cMovie.moveToFirst());
//
//        assertTrue("Error: This means that the database has not been created correctly",
//                cTrailer.moveToFirst());
//
//        assertTrue("Error: This means that the database has not been created correctly",
//                cReview.moveToFirst());
//
//        assertTrue("Error: This means that the database has not been created correctly",
//                cFavorite.moveToFirst());
//
//        // Verify that the tables have been created
//        do {
//            tableNameHashSet.remove(cMovie.getString(0));
//        } while (cMovie.moveToNext() );
//
//        do {
//            tableNameHashSet.remove(cTrailer.getString(0));
//        } while (cTrailer.moveToNext() );
//        do {
//            tableNameHashSet.remove(cReview.getString(0));
//        } while (cReview.moveToNext() );
//        do {
//            tableNameHashSet.remove(cFavorite.getString(0));
//        } while (cFavorite.moveToNext() );
//
//
//
//        // If this fails, it means that your database doesn't contain all the tables
//        assertTrue("Error: Your database was created without all of the tables",
//                tableNameHashSet.isEmpty());
//
//
//
//        // Now, does our movie table contain the correct columns?
//        cMovie = db.rawQuery("PRAGMA table_info(" + MovieContract.MovieEntry.TABLE_NAME + ")",
//                null);
//
//        assertTrue("Error: This means that we were unable to query the database for movie table information.",
//                cMovie.moveToFirst());
//
//        // Build a HashSet of all of the column names we want to look for in the movies table
//        final HashSet<String> movieColumnHashSet = new HashSet<String>();
//        movieColumnHashSet.add(MovieContract.MovieEntry._ID);
//        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_TITLE);
//        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_IMAGE_PATH);
//        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_OVERVIEW);
//        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_RELEASE);
//        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_RATING);
//        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
//
//        int movieColumnNameIndex = cMovie.getColumnIndex("name");
//        do {
//            String movieColumnName = cMovie.getString(movieColumnNameIndex);
//            movieColumnHashSet.remove(movieColumnName);
//        } while (cMovie.moveToNext());
//
//        assertTrue("Error: The database doesn't contain all of the required movie entry columns",
//                movieColumnHashSet.isEmpty());
//
//
//
//        // Now, does our trailer table contain the correct columns?
//        cTrailer = db.rawQuery("PRAGMA table_info(" + MovieContract.TrailerEntry.TABLE_NAME + ")",
//                null);
//
//        assertTrue("Error: This means that we were unable to query the database for trailer table information.",
//                cTrailer.moveToFirst());
//
//        // Build a HashSet of all of the column names we want to look for in the trailers table
//        final HashSet<String> trailerColumnHashSet = new HashSet<String>();
//        trailerColumnHashSet.add(MovieContract.TrailerEntry._ID);
//        trailerColumnHashSet.add(MovieContract.TrailerEntry.COLUMN_MOVIE_ID);
//        trailerColumnHashSet.add(MovieContract.TrailerEntry.COLUMN_TRAILER_KEY);
//
//        int trailerColumnNameIndex = cTrailer.getColumnIndex("name");
//        do {
//            String trailerColumnName = cTrailer.getString(trailerColumnNameIndex);
//            trailerColumnHashSet.remove(trailerColumnName);
//        } while (cTrailer.moveToNext());
//
//        assertTrue("Error: The database doesn't contain all of the required trailer entry columns",
//                trailerColumnHashSet.isEmpty());
//
//
//        // Now, does our review table contain the correct columns?
//        cReview = db.rawQuery("PRAGMA table_info(" + MovieContract.ReviewEntry.TABLE_NAME + ")",
//                null);
//
//        assertTrue("Error: This means that we were unable to query the database for review table information.",
//                cReview.moveToFirst());
//
//        // Build a HashSet of all of the column names we want to look for in the trailers table
//        final HashSet<String> reviewColumnHashSet = new HashSet<String>();
//        reviewColumnHashSet.add(MovieContract.ReviewEntry._ID);
//        reviewColumnHashSet.add(MovieContract.ReviewEntry.COLUMN_MOVIE_ID);
//        reviewColumnHashSet.add(MovieContract.ReviewEntry.COLUMN_AUTHOR);
//        reviewColumnHashSet.add(MovieContract.ReviewEntry.COLUMN_REVIEW);
//
//        int reviewColumnNameIndex = cReview.getColumnIndex("name");
//        do {
//            String reviewColumnName = cReview.getString(reviewColumnNameIndex);
//            reviewColumnHashSet.remove(reviewColumnName);
//        } while (cReview.moveToNext());
//
//        assertTrue("Error: The database doesn't contain all of the required favorite entry columns",
//                reviewColumnHashSet.isEmpty());
//
//
//        // Now, does ourfavorite table contain the correct columns?
//        cFavorite = db.rawQuery("PRAGMA table_info(" + MovieContract.FavoriteEntry.TABLE_NAME + ")",
//                null);
//
//        assertTrue("Error: This means that we were unable to query the database for favorite table information.",
//                cFavorite.moveToFirst());
//
//        // Build a HashSet of all of the column names we want to look for in the trailers table
//        final HashSet<String> favoriteColumnHashSet = new HashSet<String>();
//        favoriteColumnHashSet.add(MovieContract.FavoriteEntry._ID);
//        favoriteColumnHashSet.add(MovieContract.FavoriteEntry.COLUMN_MOVIE_ID);
//
//
//        int favoriteColumnNameIndex = cFavorite.getColumnIndex("name");
//        do {
//            String favoriteColumnName = cFavorite.getString(favoriteColumnNameIndex);
//            favoriteColumnHashSet.remove(favoriteColumnName);
//        } while (cFavorite.moveToNext());
//
//        assertTrue("Error: The database doesn't contain all of the required favorite entry columns",
//                favoriteColumnHashSet.isEmpty());
//
//        db.close();
//    }
//
//    // This method tests the movie table
//    public long testMovieTable() {
//
//        // Get a reference to a writeable database
//        //  If there is an error in those massive SQL table creation Strings,
//        // errors will be thrown here when you try to get a writeable database.
//        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Create ContentValues of what you want to insert
//        // Use the createMovieValues from TestUtilities
//        ContentValues testMovieValues = TestUtilities.createMovieValues();
//
//        // Insert ContentValues into the database and get a row ID back
//        long movieRowId;
//        movieRowId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, testMovieValues);
//
//        // Verify we got a row back
//        assertTrue(movieRowId != -1);
//
//        // Data is inserted.  IN THEORY.  Now pull some out to take a look and make sure it made the round trip
//        //  Query the database and receive a Cursor back
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = db.query(MovieContract.MovieEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//                );
//
//        // Move the cursor to a valid database row and check to see if we got any records back from the query
//        assertTrue("Error: No records returned from movie query", cursor.moveToFirst());
//
//        // Validate data in resulting Cursor with the original ContentValues
//        TestUtilities.validateCurrentRecord("Error: Movie Query Validation Failed", cursor, testMovieValues);
//
//        // Move the cursor to demonstrate that there is only one record in the database
//        assertFalse("Error: More than one record returned from movie query", cursor.moveToNext());
//
//        // Close the Cursor and the Database
//        cursor.close();
//        db.close();
//
//        return movieRowId;
//    }
//
//    // This method tests the trailer table
//    public long testTrailerTable() {
//
//        // Get a reference to a writeable database
//        //  If there is an error in those massive SQL table creation Strings,
//        // errors will be thrown here when you try to get a writeable database.
//        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Create ContentValues of what you want to insert
//        // Use the createMovieValues from TestUtilities
//        ContentValues testTrailerValues = TestUtilities.createTrailerValues();
//
//        // Insert ContentValues into the database and get a row ID back
//        long trailerRowId;
//        trailerRowId = db.insert(MovieContract.TrailerEntry.TABLE_NAME, null, testTrailerValues);
//
//        // Verify we got a row back
//        assertTrue(trailerRowId != -1);
//
//        // Data is inserted.  IN THEORY.  Now pull some out to take a look and make sure it made the round trip
//        //  Query the database and receive a Cursor back
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = db.query(MovieContract.TrailerEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Move the cursor to a valid database row and check to see if we got any records back from the query
//        assertTrue("Error: No records returned from trailer query", cursor.moveToFirst());
//
//        // Validate data in resulting Cursor with the original ContentValues
//        TestUtilities.validateCurrentRecord("Error: Trailer Query Validation Failed", cursor, testTrailerValues);
//
//        // Move the cursor to demonstrate that there is only one record in the database
//        assertFalse("Error: More than one record returned from trailer query", cursor.moveToNext());
//
//        // Close the Cursor and the Database
//        cursor.close();
//        db.close();
//
//        return trailerRowId;
//    }
//
//    // This method tests the reviews table
//    public long testReviewTable() {
//
//        // Get a reference to a writeable database
//        //  If there is an error in those massive SQL table creation Strings,
//        // errors will be thrown here when you try to get a writeable database.
//        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Create ContentValues of what you want to insert
//        // Use the createMovieValues from TestUtilities
//        ContentValues testReviewValues = TestUtilities.createReviewValues();
//
//        // Insert ContentValues into the database and get a row ID back
//        long reviewRowId;
//        reviewRowId = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, testReviewValues);
//
//        // Verify we got a row back
//        assertTrue(reviewRowId != -1);
//
//        // Data is inserted.  IN THEORY.  Now pull some out to take a look and make sure it made the round trip
//        //  Query the database and receive a Cursor back
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = db.query(MovieContract.ReviewEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Move the cursor to a valid database row and check to see if we got any records back from the query
//        assertTrue("Error: No records returned from review query", cursor.moveToFirst());
//
//        // Validate data in resulting Cursor with the original ContentValues
//        TestUtilities.validateCurrentRecord("Error: Review Query Validation Failed", cursor, testReviewValues);
//
//        // Move the cursor to demonstrate that there is only one record in the database
//        assertFalse("Error: More than one record returned from review query", cursor.moveToNext());
//
//        // Close the Cursor and the Database
//        cursor.close();
//        db.close();
//
//        return reviewRowId;
//    }
//
//    // This method tests the favorites table
//    public long testFavoriteTable() {
//
//        // Get a reference to a writeable database
//        //  If there is an error in those massive SQL table creation Strings,
//        // errors will be thrown here when you try to get a writeable database.
//        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Create ContentValues of what you want to insert
//        // Use the createMovieValues from TestUtilities
//        ContentValues testFavoriteValues = TestUtilities.createFavoriteValues();
//
//        // Insert ContentValues into the database and get a row ID back
//        long favoriteRowId;
//        favoriteRowId = db.insert(MovieContract.FavoriteEntry.TABLE_NAME, null, testFavoriteValues);
//
//        // Verify we got a row back
//        assertTrue(favoriteRowId != -1);
//
//        // Data is inserted.  IN THEORY.  Now pull some out to take a look and make sure it made the round trip
//        //  Query the database and receive a Cursor back
//        // A cursor is your primary interface to the query results.
//        Cursor cursor = db.query(MovieContract.FavoriteEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Move the cursor to a valid database row and check to see if we got any records back from the query
//        assertTrue("Error: No records returned from favorite query", cursor.moveToFirst());
//
//        // Validate data in resulting Cursor with the original ContentValues
//        TestUtilities.validateCurrentRecord("Error: Favorite Query Validation Failed", cursor, testFavoriteValues);
//
//        // Move the cursor to demonstrate that there is only one record in the database
//        assertFalse("Error: More than one record returned from favorite query", cursor.moveToNext());
//
//        // Close the Cursor and the Database
//        cursor.close();
//        db.close();
//
//        return favoriteRowId;
//    }
//}
