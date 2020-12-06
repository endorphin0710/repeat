package com.behemoth.repeat.recyclerView.mark;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Chapter;

import java.util.ArrayList;

public class MarkChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Chapter> mList;
    private MarkClickListener mListener;

    class ChapterViewHolder extends RecyclerView.ViewHolder {

        protected TextView chapter;
        protected TextView repeat;
        protected ImageView btnMark;

        public ChapterViewHolder(View view) {
            super(view);
            this.chapter = view.findViewById(R.id.tvChapter);
            this.repeat = view.findViewById(R.id.tvRepeat);
            this.btnMark = view.findViewById(R.id.btnMark);
        }
    }

    public MarkChapterAdapter(ArrayList<Chapter> list, MarkClickListener listener) {
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mark_chapter, viewGroup, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ChapterViewHolder chapterViewHolder = (ChapterViewHolder)holder;

        Chapter c = mList.get(position);

        chapterViewHolder.chapter.setText(String.valueOf(c.getChapterNumber()));
        chapterViewHolder.repeat.setText(String.valueOf(c.getRepeatCount()));
        chapterViewHolder.btnMark.setOnClickListener(view ->{
            mListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
