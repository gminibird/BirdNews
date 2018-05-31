package com.zrj.birdnews.util;

import android.util.Log;

/**
 * Created by a on 2018/3/17.
 */

public class MyLog {

    private static  boolean isVisible = true;

    public static void e(String tag,String msg){
        if (isVisible){
            Log.e(tag,msg);
        }
    }

    public static  void w(String tag,String msg){
        if (isVisible){
            Log.w(tag,msg);
        }
    }

    public static void v(String tag,String msg){
        if (isVisible){
            Log.v(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if (isVisible){
            Log.d(tag,msg);
        }
    }
}

