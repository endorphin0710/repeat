package com.behemoth.repeat.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface RetrofitService {

    @Headers({
            "Accept: */*"
    })
    @GET("v1/nid/me")
    Call<JsonObject> me(@Header("Authorization") String token);



}
