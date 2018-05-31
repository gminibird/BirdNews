package com.zrj.birdnews.ui.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gminibird.birdrecyclerview.adapter.RecyclerViewHolder;
import com.zrj.birdnews.R;
import com.zrj.birdnews.entities.NewsReturned;

/**
 * Created by a on 2018/3/12.
 */

public class NewsItemWithoutImg extends NewsItem {


    public NewsItemWithoutImg(NewsReturned.News news){
        super(news);
    }


    @Override
    protected void convertView(RecyclerViewHolder holder) {
        TextView title = holder.getViewById(R.id.title);
        TextView source = holder.getViewById(R.id.source);
        title.setText(mNews.getTitle());
        source.setText(mNews.getSource());
    }

    @Override
    public View getView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.news_item_without_img,parent,false);
    }

}
