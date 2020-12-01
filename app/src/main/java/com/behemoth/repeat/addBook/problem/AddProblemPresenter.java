package com.behemoth.repeat.addBook.problem;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.recyclerView.problem.ProblemAdapter;

import java.util.ArrayList;

public class AddProblemPresenter implements AddProblemContract.Presenter{

    private static final String TAG = "AddProblemPresenter";

    private final AddProblemContract.View view;
    private final Context viewContext;
    private final AddProblemContract.Model model;

    private Book newBook;
    private ProblemAdapter mAdapter;
    private boolean uploadClicked;

    public AddProblemPresenter(AddProblemContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new AddProblemModel(this);
    }

    @Override
    public void setRecyclerView(Book newBook) {
        this.newBook = newBook;
        RecyclerView mRecyclerView = ((AddProblemActivity)view).findViewById(R.id.problemRecyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(viewContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ArrayList<Chapter> mArrayList = new ArrayList<>();

        int cnt = newBook.getChapterCount();
        for(int i = 1; i <= cnt; i++){
            mArrayList.add(new Chapter(i));
        }
        mArrayList.add(new Chapter(-1));

        mAdapter = new ProblemAdapter(viewContext, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void upload() {
        if (uploadClicked) return;
        ArrayList<Chapter> chapters = mAdapter.getmList();
        model.saveBookInfo(newBook, chapters);
        uploadClicked = true;
    }

    @Override
    public void onUploadSuccess() {
        view.onUploadSuccess();
    }
}
