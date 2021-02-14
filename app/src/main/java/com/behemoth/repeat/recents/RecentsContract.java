package com.behemoth.repeat.recents;

import android.content.Context;

import com.behemoth.repeat.model.Book;

import java.util.List;

public interface RecentsContract {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void setRecyclerView();
    }

    interface Model{
    }

}
