package com.behemoth.repeat.main;

import com.behemoth.repeat.model.Book;

import java.util.List;

public interface MainContract {

    interface View{
        void showToast(String str);
        void newBook();
    }

    interface Presenter{
        List<Book> getBooks();
        void onClick(int position);
    }

    interface Model{

    }

}
