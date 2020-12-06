package com.behemoth.repeat.recyclerView.mark;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.problem.AddProblemActivity;
import com.behemoth.repeat.mark.repeat.MarkRepeatActivity;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Problem;
import com.behemoth.repeat.recyclerView.problem.ProblemAdapter;

import java.util.ArrayList;

public class MarkRepeatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Problem> mList;
    private MarkRepeatActivity parent;

    class ProblemViewHolder extends RecyclerView.ViewHolder {

        protected TextView problemNumber;
        protected RadioGroup radioGroup;
        protected RadioButton radioCorrect;
        protected RadioButton radioIncorrect;
        protected RadioButton radioNotSolved;

        public ProblemViewHolder(View view) {
            super(view);
            this.problemNumber = view.findViewById(R.id.tvProblemNumber);
            this.radioGroup = view.findViewById(R.id.radioGroup);
            this.radioCorrect = view.findViewById(R.id.radioCorrect);
            this.radioIncorrect = view.findViewById(R.id.radioIncorrect);
            this.radioNotSolved = view.findViewById(R.id.radioNotSolved);
        }
    }

    class CompleteViewHolder extends RecyclerView.ViewHolder {

        protected Button btnComplete;

        public CompleteViewHolder(View view) {
            super(view);
            this.btnComplete = view.findViewById(R.id.problemBtnNext);
        }
    }

    public MarkRepeatAdapter(Context ctx, ArrayList<Problem> list) {
        this.parent = (MarkRepeatActivity)ctx;
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
                    .inflate(R.layout.mark_repeat, viewGroup, false);
            return new ProblemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder.getItemViewType() >= mList.size()-1){
            CompleteViewHolder completeViewHolder = (CompleteViewHolder)holder;
            completeViewHolder.btnComplete.setOnClickListener(view -> {
                mList.remove(mList.size()-1);
                parent.mark(mList);
            });
        }else{
            ProblemViewHolder problemViewHolder = (ProblemViewHolder)holder;

            Problem p = mList.get(position);

            problemViewHolder.problemNumber.setText(String.valueOf(p.getProblemNumber()));

            int state = mList.get(position).getState();
            switch(state){
                case 0:
                    problemViewHolder.radioIncorrect.setChecked(true);
                    break;
                case 1:
                    problemViewHolder.radioCorrect.setChecked(true);
                    break;
            }

            problemViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.radioCorrect:
                            p.setState(1);
                            break;
                        case R.id.radioIncorrect:
                            p.setState(0);
                            break;
                        case R.id.radioNotSolved:
                            p.setState(-1);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
