package com.zrj.birdnews.presenter;


import com.gminibird.birdrecyclerview.item.IRecyclerItem;
import com.gminibird.birdrecyclerview.item.RecyclerItem;
import com.zrj.birdnews.entities.NewsReturned;
import com.zrj.birdnews.listerner.RequestCallback;
import com.zrj.birdnews.net.NewsApiImpl;
import com.zrj.birdnews.ui.item.CategoryItem;
import com.zrj.birdnews.ui.item.NewsItemWith1Img;
import com.zrj.birdnews.ui.item.NewsItemWith3Imgs;
import com.zrj.birdnews.ui.item.NewsItemWithoutImg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2018/3/11.
 */

public class NewsPresenter extends BasePresenter {


    public static final int FLAG_APPEND_ITEMS = 0;
    public static final int FLAG_NEW_ITEMS = 1;
    private String mCategory = null;
    private int mCurrPages = 0;


    private NewsApiImpl mApi = new NewsApiImpl();
    private RequestCallback<List<NewsReturned.News>> mNewCallback;
    private RequestCallback<List<NewsReturned.News>> mAppendCallback;

    public NewsPresenter() {

    }

    public void setCategory(String category) {
        mCategory = category;
        mCurrPages = 0;
    }

    private void fetch(RequestCallback<List<NewsReturned.News>> callback) {
        if (isViewAttached()) {
            getView().showLoading();
        }
        mApi.setParams(NewsApiImpl.PARAMS_PAGE, String.valueOf(mCurrPages + 1));
        mApi.setParams(NewsApiImpl.PARAMS_CHANNEL_NAME, mCategory);
        mApi.fetchItems(callback);
    }

    public void fetchItemList() {
        if (mNewCallback == null) {
            mNewCallback = new RequestCallback<List<NewsReturned.News>>() {
                @Override
                public void onSuccess(List<NewsReturned.News> data) {
                    if (isViewAttached()) {
                        getView().updateData(createItemList(data), FLAG_NEW_ITEMS);
                        mCurrPages++;
                    }
                }

                @Override
                public void onFailure(String msg) {
                    if (isViewAttached()) {
                        getView().hideLoading();
                    }
                }

                @Override
                public void onError(String msg) {
                    if (isViewAttached()) {
                        getView().hideLoading();
                    }
                }
            };
        }
        fetch(mNewCallback);
    }


    public void appendItems() {
        if (mAppendCallback == null) {
            mAppendCallback = new RequestCallback<List<NewsReturned.News>>() {
                @Override
                public void onSuccess(List<NewsReturned.News> data) {
                    if (isViewAttached()) {
                        getView().updateData(createItemList(data), FLAG_APPEND_ITEMS);
                        mCurrPages++;
                    }
                }

                @Override
                public void onFailure(String msg) {
                    if (isViewAttached()) {
                        getView().hideLoading();
                    }
                }

                @Override
                public void onError(String msg) {
                    if (isViewAttached()) {
                        getView().hideLoading();
                    }
                }
            };
        }
        fetch(mAppendCallback);
    }

    public void setRequestParam(String key, String value) {
        mApi.setParams(key, value);
    }


    private List<RecyclerItem> createItemList(List<NewsReturned.News> newsList) {

        List<RecyclerItem> itemList = new ArrayList<>();
        for (NewsReturned.News news : newsList) {
            RecyclerItem item;
            switch (news.getImgUrls().size()) {
                case 0:
                    item = new NewsItemWithoutImg(news);
                    itemList.add(item);
                    break;
                case 1:
                case 2:
                    item = new NewsItemWith1Img(news);
                    itemList.add(item);
                    break;
                default:
                    item = new NewsItemWith3Imgs(news);
                    itemList.add(item);
                    break;
            }
        }
        return itemList;
    }

    public List<IRecyclerItem> createCategoryList() {
        List<IRecyclerItem> items = new ArrayList<>();
        CategoryItem item;
        item = new CategoryItem("全部", NewsApiImpl.CATEGORY_ALL);
        items.add(item);
        item = new CategoryItem("社会", NewsApiImpl.CATEGORY_SOCIETY);
        items.add(item);
        item = new CategoryItem("体育", NewsApiImpl.CATEGORY_SPORTS);
        items.add(item);
        item = new CategoryItem("军事", NewsApiImpl.CATEGORY_MILITARY);
        items.add(item);
        item = new CategoryItem("娱乐", NewsApiImpl.CATEGORY_ENTERTAINMENT);
        items.add(item);
        item = new CategoryItem("科技", NewsApiImpl.CATEGORY_TECH);
        items.add(item);
        item = new CategoryItem("互联网", NewsApiImpl.CATEGORY_INTERNET);
        items.add(item);
        item = new CategoryItem("财经", NewsApiImpl.CATEGORY_FINANCE_AND_ECONOMICS);
        items.add(item);
        item = new CategoryItem("游戏", NewsApiImpl.CATEGORY_GAMES);
        items.add(item);
        item = new CategoryItem("教育", NewsApiImpl.CATEGORY_EDUCATION);
        items.add(item);
        item = new CategoryItem("汽车", NewsApiImpl.CATEGORY_CARS);
        items.add(item);
        item = new CategoryItem("女性", NewsApiImpl.CATEGORY_WOMEN);
        items.add(item);
        item = new CategoryItem("国内", NewsApiImpl.CATEGORY_COUNTRY);
        items.add(item);
        item = new CategoryItem("国外", NewsApiImpl.CATEGORY_INTERNATIONAL);
        items.add(item);
        return items;
    }

}
