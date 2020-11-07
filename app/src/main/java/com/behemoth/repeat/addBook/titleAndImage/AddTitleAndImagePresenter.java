package com.behemoth.repeat.addBook.titleAndImage;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;

public class AddTitleAndImagePresenter implements AddTitleAndImageContract.Presenter{

    private AddTitleAndImageContract.View view;

    public AddTitleAndImagePresenter(AddTitleAndImageContract.View view){
        this.view = view;
    }

    @Override
    public boolean validateInput(String title) {
        if(title.isEmpty()){
            view.Toast("도서명을 입력해주세요.");
            return false;
        }
        return true;
    }

    @Override
    public Book getBook(String title, Uri bookImage) {
        Book newBook = new Book();
        newBook.setTitle(title);
        if(bookImage != null){
            newBook.setImageUri(bookImage);
        }
        return newBook;
    }

}
