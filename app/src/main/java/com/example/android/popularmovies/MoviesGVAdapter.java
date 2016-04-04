package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by T510 Owner on 1/16/2016.
 */
public class MoviesGVAdapter extends BaseAdapter {


    private List<MovieResponse.movieEntity> mMovieitem;
    private Context mContext;
    private LayoutInflater inflater;


    public MoviesGVAdapter(Context mContext, List<MovieResponse.movieEntity> mMovieitem) {
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
        MovieResponse.movieEntity item = (MovieResponse.movieEntity) getItem(position);

        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.movie_image);

        String imageUrl = item.getPoster_path();

        Picasso.with(mContext)
                .load(imageUrl)
                .resize(185, 256)
                .centerInside()
                .placeholder(R.drawable.noimage)
                .into(thumbnail);
        return rowView;
    }
}
