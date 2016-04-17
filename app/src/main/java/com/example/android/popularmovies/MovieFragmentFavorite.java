package com.example.android.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.popularmovies.data.MovieContract;


public class MovieFragmentFavorite extends Fragment {


    public MovieFragmentFavorite() {
    }

    // Member variables
    Cursor mCursor;
    String LOG_TAG = "MovieFragmentFavorite";
    GridView mGridView;
    String[] mStringArray;
    TextView mTextView;
    View mRootView;
    View mNoFavView;
    private MovieFragment.MovieInterface movieInterface;


    //  When the system is ready for the Fragment to appear, this displays the Fragment's View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  If the string array isn't null
        if (favStringArray() != null) {

            //return the view containing the favorite movies images
            return FavView(inflater, container, savedInstanceState);

        }
        //  If the string array is null return the empty view
        return noFavView(inflater, container, savedInstanceState);


    }

    public View FavView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
        //  Getting a view to inflate
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        //  Getting a gridview to tie to
        mGridView = (GridView) mRootView.findViewById(R.id.gridview_movies);
        //  Setting the adapter on the gridview
        mGridView.setAdapter(new GVAdapterMovieFavorite(getActivity(), mStringArray));


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String selectedItem = parent.getItemAtPosition(position).toString();

                Cursor movieCursor = getActivity().getContentResolver().query(
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        "image_path=?",
                        new String[]{selectedItem},
                        null
                );


                movieCursor.moveToFirst();
                String movieTitle = movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
                String movieThumbnail = movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE_PATH));
                String movieOverview = movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
                String movieRelease = movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE));
                String movieRating = movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING));
                String movieMovieId = movieCursor.getString(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));


                Intent i = new Intent(getActivity(), MovieDetail.class);
                i.putExtra("movie_title", String.valueOf(movieTitle));
                i.putExtra("movie_thumbnail", String.valueOf(movieThumbnail));
                i.putExtra("movie_overview", String.valueOf(movieOverview));
                i.putExtra("movie_releasedate", String.valueOf(movieRelease));
                i.putExtra("movie_rating", String.valueOf(movieRating));
                i.putExtra("movie_id", String.valueOf(movieMovieId));
                movieInterface.showMovieDetail(i);

                movieCursor.close();


            }
        });


        //  Returning the Favorite movies view
        return mRootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //  If context has a MovieInterface
        if (context instanceof MovieFragment.MovieInterface) {
            //  Set member variable to MovieInterface of the context
            movieInterface = (MovieFragment.MovieInterface) context;
            //  This .showMovieDetail is defined in MainActivity

        } else {
            throw new Error("Needs to implement MovieInterface");
        }
    }


    public View noFavView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {
        mNoFavView = inflater.inflate(R.layout.no_favorite, container, false);
        mTextView = (TextView) mNoFavView.findViewById(R.id.no_favorite);
        mTextView.setText("Add some Favorites!");


        return mNoFavView;

    }


    public String[] favStringArray() {

        //  Cursor the content provider for the columns
        mCursor = getActivity().getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                new String[]{
                        MovieContract.MovieEntry._ID,
                        MovieContract.MovieEntry.COLUMN_TITLE,
                        MovieContract.MovieEntry.COLUMN_IMAGE_PATH,
                        MovieContract.MovieEntry.COLUMN_OVERVIEW,
                        MovieContract.MovieEntry.COLUMN_RELEASE,
                        MovieContract.MovieEntry.COLUMN_RATING,
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID
                },
                null,
                null,
                null);

        //  Count the elements returned from the cursor
        mStringArray = new String[mCursor.getCount()];

        //  For loop iterating through the cursor
        for (int i = 0; i < mCursor.getCount(); i++) {


            while (mCursor.moveToNext()) {
                String imageUrl = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE_PATH));
                mStringArray[i] = imageUrl;
                i++;

            }
        }
        return mStringArray;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
    }


}
