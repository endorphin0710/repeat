package com.behemoth.repeat.util;

import android.os.Build;
import android.util.Log;

import com.behemoth.repeat.BuildConfig;

public class LogUtil {

    public static void d(String tag, String log){
        if(BuildConfig.DEBUG){
            Log.d(tag, log);
        }
    }

}
