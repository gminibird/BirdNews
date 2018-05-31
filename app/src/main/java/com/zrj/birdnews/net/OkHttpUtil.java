package com.zrj.birdnews.net;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by a on 2018/3/13.
 */

public class OkHttpUtil {

    private static OkHttpUtil mInstance;
    private OkHttpClient mClient;


    private OkHttpUtil(){
        mClient = new OkHttpClient();
    }

    public static OkHttpUtil getInstance(){
        if (mInstance==null){
            synchronized (OkHttpUtil.class){
                if (mInstance==null){
                    mInstance = new OkHttpUtil();
                }
            }

        }
        return mInstance;
    }

    public void get(String url, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(callback);
        Log.e("===========url:",url);
    }

}
