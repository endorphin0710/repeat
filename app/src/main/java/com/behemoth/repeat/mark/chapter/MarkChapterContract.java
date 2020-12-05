package com.behemoth.repeat.mark.chapter;

import android.content.Context;

import com.behemoth.repeat.model.Book;

public interface MarkChapterContract {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void setRecyclerView(Book b);
    }

    interface Model{
    }

}
