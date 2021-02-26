package com.behemoth.repeat.stats.book;

import android.content.Context;

import com.behemoth.repeat.model.Book;

public class BookStatsPresenter implements BookStatsContract.Presenter{

    private final Context viewContext;
    private final BookStatsContract.View view;
    private final BookStatsContract.Model model;

    public BookStatsPresenter(BookStatsContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new BookStatsModel(this);
    }

    @Override
    public void getBookInfo(String id) {
        model.getBookInfo(id);
    }

    @Override
    public void onRetrieveBook(Book b) {
        view.onRetrieveBook(b);
    }

}
