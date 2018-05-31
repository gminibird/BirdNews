package com.zrj.birdnews.util;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by a on 2018/3/12.
 */

public class Util {

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatTime(String pattern,long time){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    private static DisplayMetrics getDisplayMetrics(Context context){
        return context.getResources().getDisplayMetrics();
    }

    public static float density(Context context){
        return getDisplayMetrics(context).density;
    }

    public static int getDisplayWidth(Context context){
        return getDisplayMetrics(context).widthPixels;
    }

    public static int dp2px(Context context,int dp){
        return  (int)(density(context)*dp+0.5f);
    }

    public static int px2dp(Context context,int px){
        return (int)(px/ density(context)+0.5f);
    }
}
