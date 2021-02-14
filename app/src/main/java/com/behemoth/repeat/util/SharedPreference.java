package com.behemoth.repeat.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static volatile SharedPreference instance;
    private final SharedPreferences sharedPreferences;

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

    public void setRefresh(String key, int value){
        this.sharedPreferences.edit().putInt(key, value).commit();
        int main = getRefresh(Constants.REFRESH_MAIN, 0);
        int mark = getRefresh(Constants.REFRESH_MARK, 0);
        int recents = getRefresh(Constants.REFRESH_RECENTS, 0);
        if(main + mark + recents >= 3){
            this.sharedPreferences.edit().putInt(Constants.DATA_CHANGED, 0).apply();
        }
    }

    public int getRefresh(String key, int def){
        return this.sharedPreferences.getInt(key, def);
    }

    public void onDataChanged(){
        setRefresh(Constants.DATA_CHANGED, 1);
        setRefresh(Constants.REFRESH_MAIN, 0);
        setRefresh(Constants.REFRESH_MARK, 0);
        setRefresh(Constants.REFRESH_RECENTS, 0);
    }

}
