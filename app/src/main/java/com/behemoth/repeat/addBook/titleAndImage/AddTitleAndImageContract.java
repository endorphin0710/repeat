package com.behemoth.repeat.addBook.titleAndImage;

import android.content.Context;
import android.net.Uri;

import com.behemoth.repeat.model.Book;

import java.util.List;

public interface AddTitleAndImageContract {

    interface View{
        void Toast(String message);
    }

    interface Presenter{
        boolean validateInput(String title);
        Book getBook(String title, Uri bookImage);
    }

    interface Model{

    }

}
