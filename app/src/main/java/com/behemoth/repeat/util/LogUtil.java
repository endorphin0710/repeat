package com.behemoth.repeat.util;

import android.util.Log;

import com.behemoth.repeat.BuildConfig;

public class LogUtil {

    public static void d(String tag, String log){
        if(BuildConfig.DEBUG){
            Log.d(tag, log);
        }
    }

    public static void e(String tag, String log){
        if(BuildConfig.DEBUG){
            Log.e(tag, log);
        }
    }

}
