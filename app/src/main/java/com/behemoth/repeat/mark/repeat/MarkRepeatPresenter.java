package com.behemoth.repeat.mark.repeat;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Problem;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.recyclerView.mark.MarkClickListener;
import com.behemoth.repeat.recyclerView.mark.MarkRepeatAdapter;

import java.util.ArrayList;
import java.util.List;

public class MarkRepeatPresenter implements MarkRepeatContract.Presenter{

    private final MarkRepeatContract.View view;
    private final MarkRepeatContract.Model model;
    private final Context viewContext;

    private ArrayList<Problem> mArrayList;
    private MarkRepeatAdapter mAdapter;
    private int chapterNumber;

    public MarkRepeatPresenter(MarkRepeatContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new MarkRepeatModel(this);
    }

    @Override
    public void setRecyclerView(int chapterNumber) {
        this.chapterNumber = chapterNumber;

        mArrayList = new ArrayList<>();

        RecyclerView mRecyclerView = ((MarkRepeatActivity)view).findViewById(R.id.markRepeatRecyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(viewContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        MarkClickListener markClickListener = position -> {
            if(checkCompletable()){
                mAdapter.activateButton();
            }else{
                mAdapter.inactivateButton();
            }
        };

        mAdapter = new MarkRepeatAdapter(viewContext, markClickListener,  mArrayList);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private boolean checkCompletable(){
        boolean completable = true;
        for(Problem p : mArrayList){
            int state = p.getState();
            if(state == -1) {
                completable = false;
                break;
            }
        }
        return completable;
    }

    @Override
    public void mark(Book b, int chapterNumber, List<Problem> problems, boolean temp) {
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
        if(!temp) r.setFinished(finished);
        r.setScore(score);

        if(temp){
            model.markTemp(b, chapterNumber, score, r.getProblemCount());
        }else{
            model.mark(b, chapterNumber, finished, score, r.getProblemCount());
        }
    }

    @Override
    public void onUpdateSuccess(boolean goToRecents) {
        view.onUpdateSuccess(goToRecents);
    }

    @Override
    public void onUpdateFailure() {
        view.onUpdateFailure();
    }

    @Override
    public void getBook(String id) {
        view.showProgressBar();
        model.getBook(id);
    }

    @Override
    public void onRetrieveBook(Book b) {
        view.hideProgressBar();
        view.onRetrieveBook(b);

        Chapter c = b.getChapter().get(chapterNumber);

        int repeatCount = c.getRepeatCount();
        List<Integer> marks = c.getRepeat().get(repeatCount-1).getMark();

        int problemCount = c.getProblemCount();
        for(int i = 0; i < problemCount; i++){
            int state = marks.get(i);
            mArrayList.add(new Problem(i+1, state));
        }
        mArrayList.add(new Problem());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void allCorrect() {
        mAdapter.allCorrect();
    }

    @Override
    public void allWrong() {
        mAdapter.allWrong();
    }

    @Override
    public void allReset() {
        mAdapter.allReset();
    }

}
