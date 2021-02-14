package com.behemoth.repeat.recents;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.problem.AddProblemActivity;
import com.behemoth.repeat.addBook.problem.AddProblemContract;
import com.behemoth.repeat.addBook.problem.AddProblemModel;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.recyclerView.problem.ProblemAdapter;
import com.behemoth.repeat.recyclerView.recents.RecentsAdapter;

import java.util.ArrayList;

public class RecentsPresenter implements RecentsContract.Presenter{

    private final Context viewContext;
    private final RecentsContract.View view;
    private final RecentsContract.Model model;

    private RecentsAdapter mAdapter;

    public RecentsPresenter(RecentsContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new RecentsModel(this);
    }

    @Override
    public void setRecyclerView() {
        RecyclerView mRecyclerView = ((RecentsActivity)view).findViewById(R.id.recentsRecyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(viewContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ArrayList<Chapter> mArrayList = new ArrayList<>();

        //int cnt = newBook.getChapterCount();
        //for(int i = 1; i <= 10; i++){
        //    mArrayList.add(new Chapter(i));
        //}
        //mArrayList.add(new Chapter(-1));

        mAdapter = new RecentsAdapter(viewContext, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
