package com.behemoth.repeat.mark.chapter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.recyclerView.mark.MarkChapterAdapter;
import com.behemoth.repeat.recyclerView.mark.MarkClickListener;

import java.util.ArrayList;

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

        MarkClickListener markClickListener = position -> view.goToMarkRepeatActivity(b, position);

        mAdapter = new MarkChapterAdapter(mArrayList, markClickListener);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
