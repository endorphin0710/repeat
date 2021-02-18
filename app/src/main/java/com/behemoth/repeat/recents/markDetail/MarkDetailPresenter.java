package com.behemoth.repeat.recents.markDetail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.model.Problem;
import com.behemoth.repeat.recents.RecentsActivity;
import com.behemoth.repeat.recyclerView.recents.MarkDetailAdapter;
import com.behemoth.repeat.recyclerView.recents.RecentsAdapter;
import com.behemoth.repeat.recyclerView.recents.RecentsClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarkDetailPresenter implements MarkDetailContract.Presenter{

    private final MarkDetailContract.View view;
    private final MarkDetailContract.Model model;

    private ArrayList<Problem> mArrayList;
    private MarkDetailAdapter mAdapter;

    public MarkDetailPresenter(MarkDetailContract.View view){
        this.view = view;
        this.model = new MarkDetailModel(this);
    }

    @Override
    public void setRecyclerView() {
        RecyclerView mRecyclerView = ((MarkDetailActivity)view).findViewById(R.id.mark_detail_recyclerview);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();

        mAdapter = new MarkDetailAdapter(view.getContext(), mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMarkDetail(Mark m) {
        this.model.getMarkDetail(m);
    }

    @Override
    public void onRetrieveMarkDetail(List<Problem> problems) {
        mArrayList.addAll(problems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void reorderByQuestion(boolean ascending) {
        if(ascending){
            Collections.sort(mArrayList, (a, b) -> a.getProblemNumber() - b.getProblemNumber());
        }else{
            Collections.sort(mArrayList, (a, b) -> b.getProblemNumber() - a.getProblemNumber());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void reorderByMark(boolean ascending) {
        if(ascending){
            Collections.sort(mArrayList, (a, b) -> {
                if(a.getState() == b.getState()){
                    return a.getProblemNumber() - b.getProblemNumber();
                }else{
                    return b.getState() - a.getState();
                }
            });
        }else{
            Collections.sort(mArrayList, (a, b) -> {
                if(b.getState() == a.getState()){
                    return a.getProblemNumber() - b.getProblemNumber();
                }else{
                    return a.getState() - b.getState();
                }
            });
        }
        mAdapter.notifyDataSetChanged();
    }

}
