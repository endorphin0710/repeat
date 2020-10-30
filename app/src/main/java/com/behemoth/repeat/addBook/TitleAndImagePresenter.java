package com.behemoth.repeat.addBook;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainContract;
import com.behemoth.repeat.model.Book;

import java.util.ArrayList;
import java.util.List;

public class TitleAndImagePresenter implements TitleAndImageContract.Presenter{

    private TitleAndImageContract.View view;

    public TitleAndImagePresenter(TitleAndImageContract.View view){
        this.view = view;
    }

}
