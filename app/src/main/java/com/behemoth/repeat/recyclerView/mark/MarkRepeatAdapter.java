package com.behemoth.repeat.recyclerView.mark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.mark.repeat.MarkRepeatActivity;
import com.behemoth.repeat.model.Problem;

import java.util.ArrayList;
import java.util.List;

public class MarkRepeatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Problem> mList;
    private final MarkRepeatActivity parent;
    private final MarkClickListener mListener;
    private Button btnComplete;
    private List<RadioGroup> radioGroups;
    //-1 -> none, 1 -> correct, 0 -> wrong
    private int defaultCheck = -99;

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
        protected Button btnSaveTemp;

        public CompleteViewHolder(View view) {
            super(view);
            this.btnComplete = view.findViewById(R.id.problem_btn_next);
            this.btnSaveTemp = view.findViewById(R.id.problem_btn_save_temp);
        }

    }

    public MarkRepeatAdapter(Context ctx, MarkClickListener markClickListener, ArrayList<Problem> list) {
        this.parent = (MarkRepeatActivity)ctx;
        this.mListener = markClickListener;
        this.mList = list;
        this.radioGroups = new ArrayList<>();
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

            completeViewHolder.btnSaveTemp.setOnClickListener(view -> {
                mList.remove(mList.size()-1);
                parent.mark(mList, true);
            });

            btnComplete = completeViewHolder.btnComplete;
            if(checkCompletable()){
                activateButton();
            }else{
                inactivateButton();
            }

            btnComplete.setOnClickListener(view -> {
                if(!checkCompletable()) return;
                mList.remove(mList.size()-1);
                parent.mark(mList, false);
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

            radioGroups.add(problemViewHolder.radioGroup);
            if(defaultCheck == -1){
                problemViewHolder.radioNotSolved.setChecked(true);
            }else if(defaultCheck == 1){
                problemViewHolder.radioCorrect.setChecked(true);
            }else if(defaultCheck == 0){
                problemViewHolder.radioIncorrect.setChecked(true);
            }

            problemViewHolder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
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
                mListener.onClick(position);
            });
        }
    }

    public void activateButton(){
        if(btnComplete == null) return;
        btnComplete.setBackground(ContextCompat.getDrawable(parent, R.drawable.button_background_yellow));
        btnComplete.setTextColor(ContextCompat.getColor(parent, R.color.black));
        btnComplete.setClickable(true);
    }

    public void inactivateButton(){
        if(btnComplete == null) return;
        btnComplete.setBackground(ContextCompat.getDrawable(parent, R.drawable.button_background_gray));
        btnComplete.setTextColor(ContextCompat.getColor(parent, R.color.black66));
        btnComplete.setClickable(false);
    }

    private boolean checkCompletable(){
        boolean completable = true;
        for(Problem p : mList){
            int state = p.getState();
            if(state == -1) {
                completable = false;
                break;
            }
        }
        return completable;
    }

    public void allCorrect(){
        defaultCheck = 1;
        for(Problem p : mList){
            p.setState(1);
        }
        for(RadioGroup rg : radioGroups){
            rg.check(R.id.radioCorrect);
        }
    }

    public void allWrong(){
        defaultCheck = 0;
        for(Problem p : mList){
            p.setState(0);
        }
        for(RadioGroup rg : radioGroups){
            rg.check(R.id.radioIncorrect);
        }
    }

    public void allReset(){
        defaultCheck = -1;
        for(Problem p : mList){
            p.setState(-1);
        }
        for(RadioGroup rg : radioGroups){
            rg.check(R.id.radioNotSolved);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
