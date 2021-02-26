package com.behemoth.repeat.recyclerView.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.stats.book.BookStatsActivity;

import java.util.ArrayList;

public class ChapterStatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Integer> mList;
    private final BookStatsActivity parent;

    class ChapterStatsViewHolder extends RecyclerView.ViewHolder {

        protected TextView chapterNumber;

        public ChapterStatsViewHolder(View view) {
            super(view);
            this.chapterNumber = view.findViewById(R.id.chapter_number);
        }
    }

    public ChapterStatsAdapter(Context ctx, ArrayList<Integer> list) {
        this.parent = (BookStatsActivity)ctx;
        this.mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chapter_list_container, viewGroup, false);
        return new ChapterStatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ChapterStatsViewHolder chapterStatsViewHolder = (ChapterStatsViewHolder)holder;
        chapterStatsViewHolder.chapterNumber.setText(mList.get(position) + "단원");
        chapterStatsViewHolder.chapterNumber.setOnClickListener( v -> parent.goToChapterStatsActivity(position));
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
