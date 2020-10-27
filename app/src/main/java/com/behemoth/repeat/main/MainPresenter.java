package com.behemoth.repeat.main;

import com.behemoth.repeat.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View view;

    public MainPresenter(MainContract.View view){
        this.view = view;
    }

    @Override
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        return books;
    }

    @Override
    public void onClick(int position) {
        if(position == 0){
            view.newBook();
        }else{

        }
    }

}
