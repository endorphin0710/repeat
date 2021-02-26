package com.behemoth.repeat.recyclerView.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.stats.chapter.ChapterStatsActivity;

import java.util.ArrayList;

public class RepeatDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Repeat> mList;
    private final ChapterStatsActivity parent;

    class RepeatDetailViewHolder extends RecyclerView.ViewHolder {

        protected ConstraintLayout container;
        protected TextView tvRepeatNum;
        protected TextView tvPercent;

        public RepeatDetailViewHolder(View view) {
            super(view);
            this.container = view.findViewById(R.id.repeat_container);
            this.tvRepeatNum = view.findViewById(R.id.tv_repeat_number);
            this.tvPercent = view.findViewById(R.id.tv_percent);
        }
    }

    public RepeatDetailAdapter(Context ctx, ArrayList<Repeat> list) {
        this.parent = (ChapterStatsActivity)ctx;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.repeat_detail, viewGroup, false);
        return new RepeatDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RepeatDetailViewHolder repeatDetailViewHolder = (RepeatDetailViewHolder)holder;
        Repeat r = mList.get(position);

        repeatDetailViewHolder.tvRepeatNum.setText(String.valueOf(r.getRepeatNumber()));
        repeatDetailViewHolder.tvPercent.setText(String.valueOf(r.getPercent()));

        repeatDetailViewHolder.container.setOnClickListener(v -> {
            parent.goToMarkDetailActivity(mList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public ArrayList<Repeat> getmList(){
        return mList;
    }

}
