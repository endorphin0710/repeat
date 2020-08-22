package com.behemoth.repeat.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static volatile SharedPreference instance;
    private SharedPreferences sharedPreferences;

    public static void init(Context ctx){
        instance = new SharedPreference(ctx);
    }

    private SharedPreference(Context ctx){
        sharedPreferences = ctx.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreference getInstance(){
        return instance;
    }

    public SharedPreference(Context ctx, String name){
        this.sharedPreferences = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value){
        this.sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String def){
        return this.sharedPreferences.getString(key, def);
    }

}
