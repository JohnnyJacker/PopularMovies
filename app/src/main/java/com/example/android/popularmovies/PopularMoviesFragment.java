package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class PopularMoviesFragment extends Fragment {


    public PopularMoviesFragment() {
    }


    GridView gridview;
    Response responseObj;
    GridViewAdapter adapter;
    Gson gson;
    AsyncHttpClient client;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        updateMovies();


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridview = (GridView) rootView.findViewById(R.id.gridview_movies);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Response.movieEntity item = (Response.movieEntity) adapter.getItem(position);

                String movieTitle = item.getTitle();
                String movieThumbnail = item.getPoster_path();
                String movieOverview = item.getOverview();
                String movieReleaseDate = item.getRelease_date();
                String movieRating = item.getVote_average();


                Intent i = new Intent(getActivity(), MovieDetail.class);
                i.putExtra("movie_title", String.valueOf(movieTitle));
                i.putExtra("movie_thumbnail", String.valueOf(movieThumbnail));
                i.putExtra("movie_overview", String.valueOf(movieOverview));
                i.putExtra("movie_releasedate", String.valueOf(movieReleaseDate));
                i.putExtra("movie_rating", String.valueOf(movieRating));
                startActivity(i);

            }
        });


        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateMovies();
    }

    @Override
    public void onStart() {
        super.onStart();
//        updateMovies();
    }

    public void updateMovies() {

        parseJson();

    }

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

        return url;
    }


    public void parseJson() {

        client = new AsyncHttpClient();
        client.get(getActivity(), builtURL(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String responseStr = new String(responseBody);
                gson = new Gson();
                responseObj = gson.fromJson(responseStr, Response.class);
                adapter = new GridViewAdapter(getActivity(), responseObj.getResults());
                gridview.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}





