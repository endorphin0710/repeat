package com.behemoth.repeat.main;

import android.content.Context;
import android.net.Uri;

import com.behemoth.repeat.model.Book;

import java.util.List;

public interface MainContract {

    interface View{
        Context getContext();
        void addNewBook();
        void updateTitleAndImage(Book book);
        void showChooseOptions(int position, Book book);
        void showProgressBar();
        void hideProgressBar();
        void onDeleteSuccess(int position);
        void viewStats(Book b);
    }

    interface Presenter{
        void setRecyclerView();
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
