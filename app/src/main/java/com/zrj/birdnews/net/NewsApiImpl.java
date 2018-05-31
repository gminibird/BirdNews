package com.zrj.birdnews.net;

import com.zrj.birdnews.entities.NewsReturned;
import com.zrj.birdnews.listerner.RequestCallback;
import com.zrj.birdnews.util.MyLog;
import com.zrj.birdnews.util.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by a on 2018/3/13.
 */

public class NewsApiImpl implements NewsApi {
    //channelName分类
    public static final String CATEGORY_ALL = "";
    public static final String CATEGORY_COUNTRY = "*国内*";
    public static final String CATEGORY_INTERNATIONAL = "*国外*";
    public static final String CATEGORY_MILITARY = "*军事*";
    public static final String CATEGORY_FINANCE_AND_ECONOMICS = "*财经*";
    public static final String CATEGORY_INTERNET = "*互联网*";
    public static final String CATEGORY_CARS = "*汽车*";
    public static final String CATEGORY_SPORTS = "*体育*";
    public static final String CATEGORY_ENTERTAINMENT = "*娱乐*";
    public static final String CATEGORY_GAMES = "*游戏*";
    public static final String CATEGORY_EDUCATION = "*教育*";
    public static final String CATEGORY_WOMEN = "*女人*";
    public static final String CATEGORY_TECH = "*科技*";
    public static final String CATEGORY_SOCIETY = "*社会*";

    //请求参数
    public static final String PARAMS_CHANNEL_ID = "channelId";
    public static final String PARAMS_CHANNEL_NAME = "channelName";
    public static final String PARAMS_TITLE = "title";
    public static final String PARAMS_PAGE = "page";
    public static final String PARAMS_NEED_CONTENT = "needContent";
    public static final String PARAMS_NEED_HTML = "needHtml";
    public static final String PARAMS_NEED_ALL_LIST = "needAllList";
    public static final String PARAMS_MAX_RESULT = "maxResult";
    public static final String PRAMS_ID = "id";
    private static final String PRAMS_APP_ID = "showapi_appid";
    private static final String PRAMS_TIMESTAMP = "showapi_timestamp";
    private static final String PRAMS_SIGN = "showapi_sign";
    private static final String PRAMS_SECRET_KEY = "87bc9899ddda4f408a8ef37df7f4be50";
    private static final String PRAMS_APP_ID_VALUE = "59483";
    private int mCurrentPage = 1;
    private String mSign = "";

    private HashMap<String, String> mParamsMap;

    public NewsApiImpl() {
        mParamsMap = new HashMap<>();
        initParams();
    }


    private void initParams() {
        mParamsMap.put(PARAMS_MAX_RESULT, "20");
        mParamsMap.put(PARAMS_NEED_ALL_LIST, "0");
        mParamsMap.put(PARAMS_NEED_CONTENT, "0");
        mParamsMap.put(PARAMS_NEED_HTML, "0");
        mParamsMap.put(PARAMS_PAGE, "1");
        mParamsMap.put(PRAMS_APP_ID, PRAMS_APP_ID_VALUE);
    }

    private void resetSign() {
        mParamsMap.put(PRAMS_TIMESTAMP, Util.formatTime("yyyyMMddHHmmss", System.currentTimeMillis()));
        mSign = "";
        appendSign(PARAMS_CHANNEL_ID);
        appendSign(PARAMS_CHANNEL_NAME);
        appendSign(PRAMS_ID);
        appendSign(PARAMS_MAX_RESULT);
        appendSign(PARAMS_NEED_ALL_LIST);
        appendSign(PARAMS_NEED_CONTENT);
        appendSign(PARAMS_NEED_HTML);
        appendSign(PARAMS_PAGE);
        appendSign(PRAMS_APP_ID);
        appendSign(PRAMS_TIMESTAMP);
        appendSign(PARAMS_TITLE);
        mSign += PRAMS_SECRET_KEY;
        mParamsMap.put(PRAMS_SIGN, Util.md5(mSign));
    }


    private void appendSign(String key) {
        if (mParamsMap.keySet().contains(key)) {
            mSign += key + mParamsMap.get(key);
        }
    }

    public void setParams(String key, String value) {
        if (value == null || value.equals("")) {
            mParamsMap.remove(key);
        } else {
            mParamsMap.put(key, value);
        }
    }

    @Override
    public void fetchItems(final RequestCallback<List<NewsReturned.News>> callback) {
        fetch(callback);
    }

    @Override
    public void fetchContent(RequestCallback<String> callback) {

    }

//    @Override
//    public void appendItems(final RequestCallback<List<NewsReturned.News>> callback) {
//        fetch(new RequestCallback<List<NewsReturned.News>>() {
//            @Override
//            public void onSuccess(List<NewsReturned.News> data) {
//                callback.onSuccess(data);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                callback.onFailure(msg);
//            }
//
//            @Override
//            public void onError(String msg) {
//                callback.onError(msg);
//            }
//        });
//    }

    private void fetch(final RequestCallback<List<NewsReturned.News>> callback) {
        OkHttpUtil.getInstance().get(getUrl(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String json = body.string();
                        NewsReturned newsData = GsonUtil.handleResponse(json, NewsReturned.class);
                        if (newsData.getCode() == 0
                                && newsData.getBody().getReturnCode() == 0) {
                            callback.onSuccess(newsData.getBody().getPage().getNewsList());
                            return;
                        }
                    }
                }
                callback.onError("error");
            }
        });
    }

    public String getUrl() {
        resetSign();
        String baseUrl = "http://route.showapi.com/109-35";
        String params = "";
        for (String key : mParamsMap.keySet()) {
            MyLog.e("key  " + key, key.hashCode() + "");
            params += key + "=" + mParamsMap.get(key) + "&";
        }
        return baseUrl + "?" + params;
    }

}
