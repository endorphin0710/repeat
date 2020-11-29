package com.behemoth.repeat.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitService {

    @Headers({
            "Accept: */*"
    })
    @GET("v1/nid/me")
    Call<JsonObject> me(@Header("Authorization") String token);

    @GET("v3/search/book")
    Call<JsonObject> searchBook(@Header("Authorization") String apiKey,
                                @Query("query") String title,
                                @Query("pagee") int page);

}
