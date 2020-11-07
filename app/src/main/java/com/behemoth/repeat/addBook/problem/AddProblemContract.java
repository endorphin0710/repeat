package com.behemoth.repeat.addBook.problem;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;

import java.util.ArrayList;

public interface AddProblemContract {

    interface View{
        void onUploadSuccess();
    }

    interface Presenter{
        void saveBookInfo(Book book, ArrayList<Chapter> chapters);
    }

    interface Model{

    }

}
