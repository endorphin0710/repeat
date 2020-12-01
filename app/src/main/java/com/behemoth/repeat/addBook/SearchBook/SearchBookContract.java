package com.behemoth.repeat.addBook.SearchBook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Search;

import java.util.List;

public interface SearchBookContract {

    interface View{
        Context getContext();
        void finishWithSearchData(Intent i);
    }

    interface Presenter{
        void searchBook(String title);
        void onSearchBooks(List<Search> searchs);
    }

    interface Model{
        void searchBook(String apiKey, String title, int page);
    }

}
