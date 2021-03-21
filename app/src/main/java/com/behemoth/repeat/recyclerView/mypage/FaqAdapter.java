package com.behemoth.repeat.recyclerView.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.FAQ;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private final ArrayList<FAQ> mList;

    class FaqViewHolder extends RecyclerView.ViewHolder {

        protected ConstraintLayout faqContainer;
        protected TextView tvMainQuestion;
        protected ImageView ivExpand;

        protected ConstraintLayout answerContainer;
        protected TextView tvMainAnswer;

        public FaqViewHolder(View view) {
            super(view);
            this.faqContainer = view.findViewById(R.id.faq_container);
            this.tvMainQuestion = view.findViewById(R.id.tv_main_question);
            this.ivExpand = view.findViewById(R.id.iv_expand);

            this.answerContainer = view.findViewById(R.id.answer_container);
            this.tvMainAnswer = view.findViewById(R.id.tv_main_answer);
        }
    }

    public FaqAdapter(Context ctx, ArrayList<FAQ> list) {
        this.mContext = ctx;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.faq, viewGroup, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        FaqViewHolder faqViewHolder = (FaqViewHolder) holder;

        FAQ faq = mList.get(position);
        faqViewHolder.tvMainQuestion.setText(faq.getQuestion());
        faqViewHolder.tvMainAnswer.setText(faq.getAnswer());


        faqViewHolder.ivExpand.setOnClickListener(v -> {
            if(faq.isExpanded()){
                faq.setExpanded(false);
                ((ImageView)v).setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_expand));
                faqViewHolder.answerContainer.setVisibility(View.GONE);
            }else{
                faq.setExpanded(true);
                ((ImageView)v).setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_shrink));
                faqViewHolder.answerContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
