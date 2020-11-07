package com.behemoth.repeat.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static Retrofit getRetrofitInstance(String url){
        return new retrofit2.Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
