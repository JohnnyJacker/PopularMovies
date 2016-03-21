package com.example.android.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.fragment_detail, container, false);


        String movie_title = getActivity().getIntent().getExtras().getString("movie_title");
        TextView tv1 = (TextView) rootview.findViewById(R.id.title_text_view);
        tv1.setText(movie_title);

        String movie_overview = getActivity().getIntent().getExtras().getString("movie_overview");
        TextView tv2 = (TextView) rootview.findViewById(R.id.overview_text_view);
        tv2.setText(movie_overview);

        String movie_releasedate = getActivity().getIntent().getExtras().getString("movie_releasedate");
        TextView tv3 = (TextView) rootview.findViewById(R.id.releasedate_text_view);
        tv3.setText(movie_releasedate);

        String movie_rating = getActivity().getIntent().getExtras().getString("movie_rating");
        TextView tv4 = (TextView) rootview.findViewById(R.id.rating_text_view);
        tv4.setText(movie_rating + "/10");

        String movie_thumbnail = getActivity().getIntent().getExtras().getString("movie_thumbnail");
        ImageView thumbnail = (ImageView) rootview.findViewById(R.id.imageView);
        Picasso.with(mContext)
                .load(movie_thumbnail)
                .resize(185, 256)
                .centerInside()
                .placeholder(R.drawable.noimage)
                .into(thumbnail);


        return rootview;
    }


}

