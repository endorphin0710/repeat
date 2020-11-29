package com.behemoth.repeat.recyclerView.problem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.problem.AddProblemActivity;
import com.behemoth.repeat.model.Chapter;

import java.util.ArrayList;

public class ProblemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Chapter> mList;
    private AddProblemActivity parent;

    class ChapterViewHolder extends RecyclerView.ViewHolder {

        protected TextView chapterNumber;
        protected TextView problemCnt;
        protected SeekBar seekBar;

        public ChapterViewHolder(View view) {
            super(view);
            this.chapterNumber = view.findViewById(R.id.chapterNumber);
            this.problemCnt = view.findViewById(R.id.problemCnt);
            this.seekBar = view.findViewById(R.id.seekBar);
        }
    }

    class CompleteViewHolder extends RecyclerView.ViewHolder {

        protected Button btnComplete;

        public CompleteViewHolder(View view) {
            super(view);
            this.btnComplete = view.findViewById(R.id.problemBtnNext);
        }
    }

    public ProblemAdapter(Context activity, ArrayList<Chapter> list) {
        this.parent = (AddProblemActivity) activity;
        this.mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == mList.size()-1){
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.complete_add_problem, viewGroup, false);
            return new CompleteViewHolder(view);
        }else{
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.add_problem, viewGroup, false);
            return new ChapterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder.getItemViewType() >= mList.size()-1){
            CompleteViewHolder completeViewHolder = (CompleteViewHolder)holder;
            completeViewHolder.btnComplete.setOnClickListener(view -> {
                parent.upload();
            });
        }else{
            ChapterViewHolder chapterViewHolder = (ChapterViewHolder)holder;
            chapterViewHolder.chapterNumber.setText(String.valueOf(mList.get(position).getChapterNumber()));
            chapterViewHolder.seekBar.setProgress(1);
            chapterViewHolder.problemCnt.setText("1");
            mList.get(position).setProblemCount(1);
            chapterViewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    chapterViewHolder.problemCnt.setText(i+"");
                    mList.get(position).setProblemCount(i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public ArrayList<Chapter> getmList(){
        return mList;
    }

}
