package com.behemoth.repeat.main;

import com.behemoth.repeat.model.Book;
import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View view;
    private MainContract.Model model;

    public MainPresenter(MainContract.View view){
        this.view = view;
        this.model = new MainModel(this);
    }

    @Override
    public void getBooks() {
        model.getBooks();
    }

    @Override
    public void onRetrieveBook(List<Book> books) {
        view.onRetrieveBook(books);
    }

    @Override
    public void deleteBook(int position, Book book) {
        model.deleteBook(position, book);
    }

    @Override
    public void onDeleteSuccess(int position) {
        view.onDeleteSuccess(position);
    }

    @Override
    public void onClick(int position) {
        if(position == 0){
            view.newBook();
        }else{

        }
    }

}
