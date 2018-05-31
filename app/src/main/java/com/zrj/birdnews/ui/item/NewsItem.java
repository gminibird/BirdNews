package com.zrj.birdnews.ui.item;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.gminibird.birdrecyclerview.adapter.RecyclerViewHolder;
import com.gminibird.birdrecyclerview.item.RecyclerItem;
import com.zrj.birdnews.R;
import com.zrj.birdnews.entities.NewsReturned;
import com.zrj.birdnews.ui.activity.NewsDetailActivity;

/**
 * Created by a on 2018/3/20.
 */

public abstract class NewsItem extends RecyclerItem {

    protected NewsReturned.News mNews;

    protected NewsItem(NewsReturned.News news){
        mNews = news;
    }

    @Override
    public int getType() {
        return getClass().getName().hashCode();
    }

    protected abstract void convertView(RecyclerViewHolder holder);

    @Override
    public void convert(final RecyclerViewHolder holder) {
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.getView().getContext();
                Intent intent = new Intent(context,NewsDetailActivity.class);
                intent.putExtra("url",mNews.getUrl());
                context.startActivity(intent);
            }
        });
        convertView(holder);
    }
}
