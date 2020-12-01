package com.behemoth.repeat.addBook.problem;

import android.content.Context;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;

import java.util.ArrayList;

public interface AddProblemContract {

    interface View{
        Context getContext();
        void onUploadSuccess();
    }

    interface Presenter{
        void setRecyclerView(Book book);
        void upload();
        void onUploadSuccess();
    }

    interface Model{
        void saveBookInfo(Book book, ArrayList<Chapter> chapters);
    }

}
