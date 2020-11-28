package com.behemoth.repeat.addBook.problem;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;

import java.util.ArrayList;

public class AddProblemPresenter implements AddProblemContract.Presenter{

    private static final String TAG = "AddProblemPresenter";

    private final AddProblemContract.View view;
    private final AddProblemContract.Model model;

    public AddProblemPresenter(AddProblemContract.View view){
        this.view = view;
        this.model = new AddProblemModel(this);
    }

    @Override
    public void saveBookInfo(Book newBook, ArrayList<Chapter> chapters) {
        model.saveBookInfo(newBook, chapters);
    }

    @Override
    public void onUploadSuccess() {
        view.onUploadSuccess();
    }
}
