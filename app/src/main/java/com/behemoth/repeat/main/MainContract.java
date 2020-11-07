package com.behemoth.repeat.main;

import android.net.Uri;

import com.behemoth.repeat.model.Book;

import java.util.List;

public interface MainContract {

    interface View{
        void showToast(String str);
        void newBook();
        void onRetrieveBook(List<Book> books);
    }

    interface Presenter{
        void getBooks();
        void onClick(int position);
    }

    interface Model{

    }

}
