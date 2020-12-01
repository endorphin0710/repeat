package com.behemoth.repeat.recyclerView.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Search;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Search> mList;
    private SearchClickListener mListener;
    private Context mContext;

    class SearchViewHolder extends RecyclerView.ViewHolder {

        protected ConstraintLayout container;
        protected TextView title;
        protected ImageView thumbnail;

        public SearchViewHolder(View view) {
            super(view);
            this.container = view.findViewById(R.id.container_search);
            this.title = view.findViewById(R.id.search_title);
            this.thumbnail = view.findViewById(R.id.search_image);
        }
    }

    public SearchAdapter(ArrayList<Search> list, SearchClickListener listener, Context ctx) {
        this.mList = list;
        this.mListener = listener;
        this.mContext = ctx;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_book, viewGroup, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SearchViewHolder searchViewHolder = (SearchViewHolder)holder;
        searchViewHolder.title.setText(mList.get(position).getTitle());

        String thumbnail = mList.get(position).getThumbnail();
        if(thumbnail.startsWith("https")){
            Glide.with(mContext)
                    .load(mList.get(position).getThumbnail())
                    .into(searchViewHolder.thumbnail);
        }

        searchViewHolder.container.setOnClickListener(view -> mListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
