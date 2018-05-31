package com.zrj.birdnews.ui.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gminibird.birdrecyclerview.adapter.RecyclerViewHolder;
import com.zrj.birdnews.R;
import com.zrj.birdnews.entities.NewsReturned;

public class SearchRelatedItem extends NewsItem {



    public SearchRelatedItem(NewsReturned.News news) {
        super(news);
    }


    @Override
    protected void convertView(RecyclerViewHolder holder) {
        TextView textView = holder.getViewById(R.id.text);
        textView.setText(mNews.getTitle());
    }

    @Override
    public View getView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.search_window_item,parent,false);
    }

}
