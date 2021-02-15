package com.behemoth.repeat.recents;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.recyclerView.recents.RecentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecentsPresenter implements RecentsContract.Presenter{

    private final Context viewContext;
    private final RecentsContract.View view;
    private final RecentsContract.Model model;

    private ArrayList<Mark> mArrayList;
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

        mArrayList = new ArrayList<>();

        mAdapter = new RecentsAdapter(viewContext, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getRecentMarks() {
        model.getRecentMarks();
    }

    @Override
    public void onRetrieveMarks(List<Mark> marks) {
        mArrayList.clear();
        mArrayList.addAll(marks);
        mAdapter.notifyDataSetChanged();
        view.hideProgressBar();
    }

}
