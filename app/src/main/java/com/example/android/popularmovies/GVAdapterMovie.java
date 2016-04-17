package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GVAdapterMovie extends BaseAdapter {


    private List<ResponseMovie.movieEntity> mMovieitem;
    private Context mContext;



    public GVAdapterMovie(Context mContext, List<ResponseMovie.movieEntity> mMovieitem) {
        this.mContext = mContext;
        this.mMovieitem = mMovieitem;
    }

    @Override
    public int getCount() {
        return mMovieitem.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovieitem.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movie_item, parent, false);
        ResponseMovie.movieEntity item = (ResponseMovie.movieEntity) getItem(position);

        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.movie_image);

        String imageUrl = item.getPoster_pathBuilt();

        Picasso.with(mContext)
                .load(imageUrl)
                .resize(185, 256)
                .centerInside()
                .placeholder(R.drawable.noimage)
                .into(thumbnail);
        return rowView;
    }
}
