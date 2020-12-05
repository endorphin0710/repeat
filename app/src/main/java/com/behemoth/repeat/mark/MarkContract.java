package com.behemoth.repeat.mark;

import android.content.Context;

import com.behemoth.repeat.model.Book;

import java.util.List;

public interface MarkContract {

    interface View{
        Context getContext();
        void goToMarkChapterActivity(Book b);
    }

    interface Presenter{
        void setRecyclerView();
        void getBooks();
        void onRetrieveBook(List<Book> books);
    }

    interface Model{
        void getBooks();
    }

}
