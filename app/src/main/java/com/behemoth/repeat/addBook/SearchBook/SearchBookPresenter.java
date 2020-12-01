package com.behemoth.repeat.addBook.SearchBook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Search;
import com.behemoth.repeat.recyclerView.search.SearchAdapter;
import com.behemoth.repeat.recyclerView.search.SearchClickListener;
import com.behemoth.repeat.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchBookPresenter implements SearchBookContract.Presenter{

    private final SearchBookContract.View view;
    private final SearchBookContract.Model model;
    private final Context viewContext;

    private ArrayList<Search> mArrayList;
    private SearchAdapter mAdapter;

    private int page;

    public SearchBookPresenter(SearchBookContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new SearchBookModel(this);
        setRecyclerView();
    }

    @Override
    public void searchBook(String title) {
        if(validateString(title)) {
            String apiKey = viewContext.getString(R.string.kakao_rest_api_key);
            page = 1;
            model.searchBook(apiKey, title, page);
        }
    }

    @Override
    public void onSearchBooks(List<Search> searches) {
        mArrayList.clear();
        mArrayList.addAll(searches);
        mAdapter.notifyDataSetChanged();
    }

    private void setRecyclerView() {
        mArrayList = new ArrayList<>();
        RecyclerView mRecyclerView = ((SearchBookActivity)view).findViewById(R.id.searchRecyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(viewContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        SearchClickListener searchClickListener = position -> {
            Search s = mArrayList.get(position);
            Intent i = new Intent();
            i.putExtra(Constants.LABEL_SEARCHED_TITLE,s.getTitle());
            i.putExtra(Constants.LABEL_SEARCHED_THUMBNAIL,s.getThumbnail());
            view.finishWithSearchData(i);
        };

        mAdapter = new SearchAdapter(mArrayList, searchClickListener, viewContext);

        mRecyclerView.setAdapter(mAdapter);
    }

    private boolean validateString(String title){
        String regExp = "^[a-zA-Z가-힣]+$";
        return title.length() > 1 && title.matches(regExp);
    }

}
