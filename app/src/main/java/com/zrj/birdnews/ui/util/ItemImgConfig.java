package com.zrj.birdnews.ui.util;

import android.content.Context;
import android.content.res.Resources;

import com.zrj.birdnews.R;
import com.zrj.birdnews.util.MyLog;
import com.zrj.birdnews.util.Util;

/**
 * Created by a on 2018/3/17.
 */

public class ItemImgConfig {

    public static int width=-1;

    public static int configWidth(Context context){
        if (width==-1) {
            MyLog.e("config===========","调用");
            int displayWidth = Util.getDisplayWidth(context);
            Resources resources = context.getResources();
            int space = resources.getDimensionPixelSize(R.dimen.news_item_img_margin) * 2 +
                    resources.getDimensionPixelSize(R.dimen.news_item_horizontal_margin)*2 +
                    resources.getDimensionPixelSize(R.dimen.news_item_content_horizontal_padding)*2;
            width = (displayWidth - space) / 3;
        }
        return width;
    }
}
