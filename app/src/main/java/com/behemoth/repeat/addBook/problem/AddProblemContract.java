package com.behemoth.repeat.addBook.problem;

import android.content.Context;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;

import java.util.ArrayList;

public interface AddProblemContract {

    interface View{
        Context getContext();
        void onUploadSuccess();
        void onUploadFailure();
        void showProgressBar();
        void hideProgressBar();
    }

    interface Presenter{
        void setRecyclerView(Book book);
        void upload();
        void onUploadSuccess();
        void onUploadFailure();
    }

    interface Model{
        void saveBookInfo(Book book, ArrayList<Chapter> chapters);
    }

}
