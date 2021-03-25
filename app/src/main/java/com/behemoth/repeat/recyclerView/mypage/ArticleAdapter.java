package com.behemoth.repeat.recyclerView.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private final ArrayList<String> mList;

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected TextView tvArticle;

        public ArticleViewHolder(View view) {
            super(view);
            this.tvTitle = view.findViewById(R.id.tv_title);
            this.tvArticle = view.findViewById(R.id.tv_article);
        }
    }

    public ArticleAdapter(Context ctx, ArrayList<String> list) {
        this.mContext = ctx;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.article, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ArticleViewHolder faqViewHolder = (ArticleViewHolder) holder;
        if(position == 0){
            faqViewHolder.tvTitle.setVisibility(View.VISIBLE);
        }
        String article = mList.get(position);
        faqViewHolder.tvArticle.setText(article);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
