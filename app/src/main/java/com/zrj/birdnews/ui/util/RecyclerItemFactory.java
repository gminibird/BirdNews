package com.zrj.birdnews.ui.util;

import com.gminibird.birdrecyclerview.item.IRecyclerItem;

/**
 * Created by a on 2018/3/22.
 */

public class RecyclerItemFactory {

    public static <T extends IRecyclerItem> T createItem(Class<T> clz){
        IRecyclerItem item =null;
        try {
            item = (IRecyclerItem) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  (T) item;
    }

}
