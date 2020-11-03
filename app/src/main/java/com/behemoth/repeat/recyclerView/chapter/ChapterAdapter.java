package com.behemoth.repeat.recyclerView.chapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Chapter;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Chapter> mList;

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

    public ChapterAdapter(ArrayList<Chapter> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.add_problem, viewGroup, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ChapterViewHolder chapterViewHolder = (ChapterViewHolder)holder;
        chapterViewHolder.chapterNumber.setText(String.valueOf(mList.get(position).getChapterNumber()));
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

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public ArrayList<Chapter> getmList(){
        return mList;
    }

}
