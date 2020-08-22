package com.behemoth.repeat;

import com.behemoth.repeat.util.SharedPreference;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreference.init(getApplicationContext());
    }
}
