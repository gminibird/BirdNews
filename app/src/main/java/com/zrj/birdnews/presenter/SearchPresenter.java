package com.zrj.birdnews.presenter;

import com.gminibird.birdrecyclerview.item.RecyclerItem;
import com.zrj.birdnews.entities.NewsReturned;
import com.zrj.birdnews.listerner.RequestCallback;
import com.zrj.birdnews.net.NewsApiImpl;
import com.zrj.birdnews.ui.item.SearchRelatedItem;
import com.zrj.birdnews.ui.view.MVPBaseView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter extends BasePresenter {

    private NewsApiImpl mApi;
    private MVPBaseView<List> mView;

    public SearchPresenter(){
        mApi = new NewsApiImpl();
    }

    public void fetchSearch(String keyWords) {
        mApi.setParams(NewsApiImpl.PARAMS_TITLE,keyWords);
        mApi.setParams(NewsApiImpl.PARAMS_PAGE,String.valueOf(1));
        mApi.setParams(NewsApiImpl.PARAMS_MAX_RESULT,String.valueOf(5));
        if (mView == null) {
            mView = getView();
        }
        mApi.fetchItems(new RequestCallback<List<NewsReturned.News>>() {
            @Override
            public void onSuccess(List<NewsReturned.News> data) {
                mView.updateData(createItemList(data));
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private List<RecyclerItem> createItemList(List<NewsReturned.News> dataList) {
        List<RecyclerItem> items = new ArrayList<>();
        SearchRelatedItem item;
        for (NewsReturned.News news : dataList) {
            item = new SearchRelatedItem(news);
            items.add(item);
        }
        return items;
    }

}
