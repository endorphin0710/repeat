package com.behemoth.repeat.mark.repeat;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Problem;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.recyclerView.mark.MarkRepeatAdapter;

import java.util.ArrayList;
import java.util.List;

public class MarkRepeatPresenter implements MarkRepeatContract.Presenter{

    private final MarkRepeatContract.View view;
    private final MarkRepeatContract.Model model;
    private final Context viewContext;

    private ArrayList<Problem> mArrayList;
    private MarkRepeatAdapter mAdapter;

    public MarkRepeatPresenter(MarkRepeatContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new MarkRepeatModel(this);
    }

    @Override
    public void setRecyclerView(Book b, int chapterNumber) {
        mArrayList = new ArrayList<>();

        Chapter c = b.getChapter().get(chapterNumber);

        int repeatCount = c.getRepeatCount();
        List<Integer> marks = c.getRepeat().get(repeatCount-1).getMark();

        int problemCount = c.getProblemCount();
        for(int i = 0; i < problemCount; i++){
            int state = marks.get(i);
            mArrayList.add(new Problem(i+1, state));
        }
        mArrayList.add(new Problem());

        RecyclerView mRecyclerView = ((MarkRepeatActivity)view).findViewById(R.id.markRepeatRecyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(viewContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MarkRepeatAdapter(viewContext, mArrayList);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void mark(Book b, int chapterNumber, List<Problem> problems) {
        boolean finished = true;

        List<Integer> marks = new ArrayList<>();
        int score = 0;
        for(Problem p : problems){
            int state = p.getState();
            if(state == -1) finished = false;
            marks.add(state);
            score += state;
        }

        Chapter c = b.getChapter().get(chapterNumber);
        int repeatCount = c.getRepeatCount();
        Repeat r = c.getRepeat().get(repeatCount-1);
        r.setMark(marks);
        r.setFinished(finished);
        r.setScore(score);

        model.mark(b, chapterNumber, finished, score, r.getProblemCount());
    }

    @Override
    public void onUpdateSuccess() {
        view.onUpdateSuccess();
    }

    @Override
    public void onUpdateFailure() {
        view.onUpdateFailure();
    }

}
