package com.behemoth.repeat.addBook.chapter;

import android.net.Uri;

import com.behemoth.repeat.addBook.titleAndImage.AddTitleAndImageContract;
import com.behemoth.repeat.model.Book;

public class AddChapterPresenter implements AddChapterContract.Presenter{

    private AddChapterContract.View view;

    public AddChapterPresenter(AddChapterContract.View view){
        this.view = view;
    }



}
