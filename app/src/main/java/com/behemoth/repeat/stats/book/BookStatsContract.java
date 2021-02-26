package com.behemoth.repeat.stats.book;

import android.content.Context;

import com.behemoth.repeat.model.Book;

public interface BookStatsContract {

    interface View{
        Context getContext();
        void onRetrieveBook(Book b);
    }

    interface Presenter{
        void getBookInfo(String id);
        void onRetrieveBook(Book b);
    }

    interface Model{
        void getBookInfo(String id);
    }

}
