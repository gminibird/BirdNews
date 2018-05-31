package com.zrj.birdnews.ui.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.gminibird.birdrecyclerview.adapter.RecyclerViewHolder;
import com.zrj.birdnews.R;
import com.zrj.birdnews.entities.NewsReturned;
import com.zrj.birdnews.ui.util.ItemImgConfig;

/**
 * Created by a on 2018/3/12.
 */

public class NewsItemWith3Imgs extends NewsItem {

    public NewsItemWith3Imgs(NewsReturned.News news) {
        super(news);
    }

    @Override
    public View getView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.news_item_with_much_img, parent, false);
    }

    @Override
    protected void convertView(RecyclerViewHolder holder) {
        TextView title = holder.getViewById(R.id.title);
        TextView source = holder.getViewById(R.id.source);
        title.setText(mNews.getTitle());
        source.setText(mNews.getSource());
        Context context = holder.getView().getContext();
        int[] imageId = {R.id.img1, R.id.img2, R.id.img3};
        RequestManager manager = Glide.with(context);
        for (int i = 0; i < 3; i++) {
            ImageView imageView = holder.getViewById(imageId[i]);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = ItemImgConfig.configWidth(context);
            params.height = params.width * 10 / 16;
            String url = mNews.getImgUrls().get(i).getUrl();
            manager.asBitmap().load(url).into(imageView);
        }
    }
}
