package com.zrj.birdnews.net;


import com.zrj.birdnews.entities.NewsReturned;
import com.zrj.birdnews.listerner.RequestCallback;

import java.util.List;

/**
 * Created by a on 2018/3/13.
 */

public interface NewsApi {

    void fetchItems(RequestCallback<List<NewsReturned.News>> callback);

    void fetchContent(RequestCallback<String> callback);



}
