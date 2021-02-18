package com.behemoth.repeat.recyclerView.recents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Problem;

import java.util.ArrayList;

public class MarkDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Problem> mList;
    private final Context mContext;

    class MarkDetailViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvProblemNum;
        protected ImageView ivMark;

        public MarkDetailViewHolder(View view) {
            super(view);
            this.tvProblemNum = view.findViewById(R.id.tv_problem_number);
            this.ivMark = view.findViewById(R.id.iv_mark);
        }
    }

    public MarkDetailAdapter(Context ctx, ArrayList<Problem> list) {
        this.mContext = ctx;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mark_detail, viewGroup, false);
        return new MarkDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MarkDetailViewHolder recentsViewHolder = (MarkDetailViewHolder)holder;
        Problem p = mList.get(position);

        recentsViewHolder.tvProblemNum.setText(String.valueOf(p.getProblemNumber()));
        int state = p.getState();
        if(state > 0){
            recentsViewHolder.ivMark.setImageResource(R.drawable.ic_o);
        }else{
            recentsViewHolder.ivMark.setImageResource(R.drawable.ic_x);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public ArrayList<Problem> getmList(){
        return mList;
    }

}
