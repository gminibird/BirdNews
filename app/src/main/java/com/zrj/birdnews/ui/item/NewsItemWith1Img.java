package com.zrj.birdnews.ui.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gminibird.birdrecyclerview.adapter.RecyclerViewHolder;
import com.zrj.birdnews.R;
import com.zrj.birdnews.entities.NewsReturned;
import com.zrj.birdnews.ui.util.ItemImgConfig;

/**
 * Created by a on 2018/3/12.
 */

public class NewsItemWith1Img extends NewsItem {



    public NewsItemWith1Img(NewsReturned.News news) {
        super(news);
    }


    @Override
    protected void convertView(RecyclerViewHolder holder) {
        TextView title = holder.getViewById(R.id.title);
        TextView source = holder.getViewById(R.id.source);
        ImageView imageView = holder.getViewById(R.id.img);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = ItemImgConfig.configWidth(imageView.getContext());
        params.height = params.width*10/16;
        title.setText(mNews.getTitle());
        source.setText(mNews.getSource());
        Glide.with(imageView)
                .asBitmap()
                .load(mNews.getImgUrls().get(0).getUrl()).into(imageView);
    }

    @Override
    public View getView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.news_item_with_few_img, parent, false);
    }

}
