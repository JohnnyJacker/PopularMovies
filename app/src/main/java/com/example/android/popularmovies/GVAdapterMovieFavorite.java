package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class GVAdapterMovieFavorite extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls;

    public GVAdapterMovieFavorite(Context context, String[] imageUrls) {
        super(context, R.layout.movie_item, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.movie_item, parent, false);
        }

        Picasso
                .with(context)
                .load(imageUrls[position])
                .resize(185, 256)
                .centerInside()
                .placeholder(R.drawable.noimage)
                .into((ImageView) convertView);

        return convertView;
    }
}
