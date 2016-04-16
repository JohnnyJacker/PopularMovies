package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class MovieFragment extends Fragment {

    //  Here is the member variable for the MovieInterface
    private MovieInterface movieInterface;
    //  This is the actual interface
    public interface MovieInterface {
        //  This is where we are declaring the name of the method to be used
        void showMovieDetail(Intent intent);
    }


    public MovieFragment() {
    }


    GridView gridview;
    ResponseMovie responseObj;
    GVAdapterMovie adapter;
    Gson gson;
    AsyncHttpClient client;
    ToggleButton favoriteToggle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

// All this method does to update the movies is call parseJson();
        updateMovies();


//        Switch favoriteToggle = (Switch) getActivity().findViewById(R.id.favorite_toggle);
//        favoriteToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {
//                    Toast.makeText(getContext(), "ON", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getContext(), "OFF", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

//        favoriteToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });


// The layout to inflate
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

// The gridview to inflate
        gridview = (GridView) rootView.findViewById(R.id.gridview_movies);

// OnItemClickListener for when a movie is clicked it gets the position clicked.
// Then it gets the info and starts an intent to send the info to
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ResponseMovie.movieEntity item = (ResponseMovie.movieEntity) adapter.getItem(position);

                String movieTitle = item.getTitle();
                String movieThumbnail = item.getPoster_path();
                String movieOverview = item.getOverview();
                String movieReleaseDate = item.getRelease_date();
                String movieRating = item.getVote_average();
                String movieId = item.getId();

                Log.d("MovieFragment", movieThumbnail);


                Intent i = new Intent(getActivity(), MovieDetail.class);
                i.putExtra("movie_title", String.valueOf(movieTitle));
                i.putExtra("movie_thumbnail", String.valueOf(movieThumbnail));
                i.putExtra("movie_overview", String.valueOf(movieOverview));
                i.putExtra("movie_releasedate", String.valueOf(movieReleaseDate));
                i.putExtra("movie_rating", String.valueOf(movieRating));
                i.putExtra("movie_id", String.valueOf(movieId));
//                startActivity(i);

                movieInterface.showMovieDetail(i);


            }
        });




        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().getIntent().getExtras() == null) return;
        updateMovies();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    // Created this method to make it more clear for when I need to update the movies
    public void updateMovies() {
        parseJson();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //  If context has a MovieInterface
        if (context instanceof MovieInterface) {
            //  Set member variable to MovieInterface of the context
            movieInterface = (MovieInterface) context;
            //  This .showMovieDetail is defined in MainActivity

        } else {
            throw new Error("Needs to implement MovieInterface");
        }
    }

    // This method is used within the parseJson();
    // Passes the built URL in for GSON library
    public String builtURL() {

        String MOVIE_BASE_URL =
                "http://api.themoviedb.org/3/discover/movie?";
        String QUERY_PARAM = "sort_by";
        String APPID_PARAM = "api_key";

        SharedPreferences categories = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String category = categories.getString(getString(R.string.pref_category_key),
                getString(R.string.pref_category_nowplaying));

        SharedPreferences orders = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order = orders.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_desc));

        Uri.Builder builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, category + order)
                .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY);

        String url = builtUri.toString();

        // This is the built url for when the app calls on the server for the grid of movies
        return url;
    }


    public void parseJson() {

        client = new AsyncHttpClient();
        client.get(getActivity(), builtURL(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String responseStr = new String(responseBody);
                gson = new Gson();
                responseObj = gson.fromJson(responseStr, ResponseMovie.class);
                adapter = new GVAdapterMovie(getActivity(), responseObj.getResults());
                gridview.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}





