package com.behemoth.repeat.mark.repeat;

import android.content.Context;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Problem;

import java.util.List;

public interface MarkRepeatContract {

    interface View{
        Context getContext();
        void onUpdateSuccess(boolean goToRecents);
        void onUpdateFailure();
        void showProgressBar();
        void hideProgressBar();
        void onRetrieveBook(Book b);
    }

    interface Presenter{
        void setRecyclerView(Book b, int chapterNumber);
        void mark(Book b, int chapterNumber, List<Problem> problems, boolean temp);
        void onUpdateSuccess(boolean goToRecents);
        void onUpdateFailure();
        void getBook(String id);
        void onRetrieveBook(Book b);
        void allCorrect();
        void allWrong();
        void allReset();
    }

    interface Model{
        void getBook(String id);
        void mark(Book b, int chapterNumber, boolean finished, int score, int problemCnt);
        void markTemp(Book b, int chapterNumber, int score, int problemCnt);
    }

}
