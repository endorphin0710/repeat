package com.behemoth.repeat.addBook.SearchBook;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.behemoth.repeat.model.Search;
import com.behemoth.repeat.retrofit.RetrofitService;
import com.behemoth.repeat.retrofit.RetrofitUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchBookModel extends AppCompatActivity implements SearchBookContract.Model {

    private static final String TAG = "SearchBookModel";

    private SearchBookContract.Presenter presenter;

    public SearchBookModel(SearchBookContract.Presenter p){
        this.presenter = p;
    }

    @Override
    public void searchBook(String apiKey, String title, int page) {
        List<Search> searches = new ArrayList<>();

        String apiURL = "https://dapi.kakao.com/";
        Retrofit retrofit = RetrofitUtil.getRetrofitInstance(apiURL);
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<JsonObject> call = retrofitService.searchBook(apiKey, title, page);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray result = (JsonArray) response.body().get("documents");
                for(int i = 0; i < result.size(); i++){
                    String title = result.get(i).getAsJsonObject().get("title").getAsString();
                    String thumbnail = result.get(i).getAsJsonObject().get("thumbnail").getAsString();
                    searches.add(new Search(title, thumbnail));
                    presenter.onSearchBooks(searches);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "FAILURE : " + t.getMessage());
            }
        });

    }
}