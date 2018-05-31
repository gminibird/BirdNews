package com.zrj.birdnews.net;

import com.google.gson.Gson;

import com.zrj.birdnews.entities.NewsReturned;

import okhttp3.Response;

/**
 * Created by a on 2018/3/14.
 */

public class GsonUtil {

    public static<T>  T handleResponse(String jsonString, Class<T> clazz){
        Gson gson = new Gson();
        T t = gson.fromJson(jsonString,clazz);
        return t;
    }
}
