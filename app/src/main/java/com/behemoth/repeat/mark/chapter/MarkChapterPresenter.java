package com.behemoth.repeat.mark.chapter;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.recyclerView.bookCard.SpaceDecoration;
import com.behemoth.repeat.recyclerView.mark.MarkChapterAdapter;

import java.util.ArrayList;
import java.util.List;

public class MarkChapterPresenter implements MarkChapterContract.Presenter{

    private final MarkChapterContract.View view;
    private final MarkChapterContract.Model model;
    private final Context viewContext;

    private ArrayList<Chapter> mArrayList;
    private MarkChapterAdapter mAdapter;

    public MarkChapterPresenter(MarkChapterContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new MarkChapterModel(this);
    }

    @Override
    public void setRecyclerView(Book b) {
        mArrayList = new ArrayList<>();

        mArrayList.addAll(b.getChapter());

        RecyclerView mRecyclerView = ((MarkChapterActivity)view).findViewById(R.id.markChapterRecyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(viewContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MarkChapterAdapter(mArrayList);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
