package com.behemoth.repeat.addBook.chapter;

import android.content.Context;

import com.behemoth.repeat.model.Book;

public interface AddChapterContract {

    interface View{
        Context getContext();
        void showToast(String message);
        void nextStep(Book newBook);
    }

    interface Presenter{
        void validateInput(String chapter, Book newBook);
    }

}
