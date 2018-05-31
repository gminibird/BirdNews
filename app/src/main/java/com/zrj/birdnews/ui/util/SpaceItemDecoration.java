package com.zrj.birdnews.ui.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by a on 2018/3/12.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mHeight;

    public SpaceItemDecoration(int height){
        mHeight =height;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = mHeight;
    }
}
