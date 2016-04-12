package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class LVAdapterTrailer extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ResponseTrailer.ResultsEntity> mPosts;

    private Context mContext;


    public LVAdapterTrailer(Context mContext, List<ResponseTrailer.ResultsEntity> mMovieitem) {
        this.mContext = mContext;
        this.mPosts = mMovieitem;
    }


    @Override
    public int getCount() {
        return mPosts.size();
    }





    @Override
    public Object getItem(int position) {


        return mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.trailer_list_item, parent, false);
        ResponseTrailer.ResultsEntity item = (ResponseTrailer.ResultsEntity) getItem(position);

        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.play_thumbnail);


        String trailerName = item.getName();

        TextView tv = (TextView) rowView.findViewById(R.id.trailer_title);
        tv.setText(trailerName);


        thumbnail.setImageResource(R.drawable.play);

        return rowView;
    }
}
