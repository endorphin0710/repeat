package com.behemoth.repeat.recents;

import android.content.Context;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Mark;

import java.util.List;

public interface RecentsContract {

    interface View{
        Context getContext();
        void getRecentMarks();
        void showProgressBar();
        void hideProgressBar();
    }

    interface Presenter{
        void setRecyclerView();
        void getRecentMarks();
        void onRetrieveMarks(List<Mark> marks);
    }

    interface Model{
        void getRecentMarks();
    }

}
