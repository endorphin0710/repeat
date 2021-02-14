package com.behemoth.repeat.recyclerView.recents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.problem.AddProblemActivity;
import com.behemoth.repeat.model.Chapter;

import java.util.ArrayList;

public class RecentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Chapter> mList;

    class RecentsViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvBook;
        protected TextView tvChapter;
        protected TextView tvRepeat;
        protected TextView tvScore;
        protected TextView tvTotal;
        protected ImageView thumbnail;

        public RecentsViewHolder(View view) {
            super(view);
        }
    }

    public RecentsAdapter(Context activity, ArrayList<Chapter> list) {
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
                .inflate(R.layout.add_problem, viewGroup, false);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RecentsViewHolder recentsViewHolder = (RecentsViewHolder)holder;
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public ArrayList<Chapter> getmList(){
        return mList;
    }

}
