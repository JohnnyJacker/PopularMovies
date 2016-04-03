package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    private Context mContext;

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

        ButterKnife.bind(this, rootview);


        String movie_title = getActivity().getIntent().getExtras().getString("movie_title");
        String movie_overview = getActivity().getIntent().getExtras().getString("movie_overview");
        String movie_releasedate = getActivity().getIntent().getExtras().getString("movie_releasedate");
        String movie_rating = getActivity().getIntent().getExtras().getString("movie_rating");
        String movie_thumbnail = getActivity().getIntent().getExtras().getString("movie_thumbnail");




        mTitle.setText(movie_title);

        mOverview.setText(movie_overview);

        mRelease.setText(movie_releasedate);

        mRating.setText(movie_rating + "/10");

        Picasso.with(mContext)
                .load(movie_thumbnail)
                .resize(185, 256)
                .centerInside()
                .placeholder(R.drawable.noimage)
                .into(mThumbnail);


        return rootview;
    }


}

