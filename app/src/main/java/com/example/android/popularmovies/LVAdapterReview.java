package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class LVAdapterReview extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ResponseReview.ResultsEntity> mPosts;

    private Context mContext;


    public LVAdapterReview(Context mContext, List<ResponseReview.ResultsEntity> mMovieitem) {
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
        View rowView = inflater.inflate(R.layout.review_list_item, parent, false);
        ResponseReview.ResultsEntity item = (ResponseReview.ResultsEntity) getItem(position);


        TextView authorTextView = (TextView) rowView.findViewById(R.id.review_author);
        TextView contentTextView = (TextView) rowView.findViewById(R.id.review_content);

        String authorName = item.getAuthor();
        String content = item.getContent();

        authorTextView.setText(authorName);
        contentTextView.setText(content);


        return rowView;
    }
}
