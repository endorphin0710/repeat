package com.behemoth.repeat.recents.markDetail;


import android.content.Context;

import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.model.Problem;

import java.util.List;

public interface MarkDetailContract {

    interface View{
        Context getContext();
    }

    interface Presenter{
        void setRecyclerView();
        void getMarkDetail(Mark m);
        void onRetrieveMarkDetail(List<Problem> problems);
        void reorderByQuestion(boolean ascending);
        void reorderByMark(boolean ascending);
    }

    interface Model{
        void getMarkDetail(Mark m);
    }

}
