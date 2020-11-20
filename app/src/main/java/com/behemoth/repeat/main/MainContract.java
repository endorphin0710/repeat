package com.behemoth.repeat.main;

import android.net.Uri;

import com.behemoth.repeat.model.Book;

import java.util.List;

public interface MainContract {

    interface View{
        void newBook();
        void onRetrieveBook(List<Book> books);
        void onDeleteSuccess(int position);
    }

    interface Presenter{
        void onClick(int position);
        void getBooks();
        void onRetrieveBook(List<Book> books);
        void deleteBook(int position, Book book);
        void onDeleteSuccess(int position);
    }

    interface Model{
        void getBooks();
        void deleteBook(int position, Book book);
    }

}
