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
import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Mark> mList;
    private final Context mContext;

    class RecentsViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected TextView tvDate;
        protected TextView tvChapter;
        protected TextView tvRepeat;
        protected TextView tvScore;
        protected TextView tvTotal;
        protected ImageView thumbnail;

        public RecentsViewHolder(View view) {
            super(view);
            this.tvTitle = view.findViewById(R.id.recent_title);
            this.tvDate = view.findViewById(R.id.recent_date);
            this.tvChapter = view.findViewById(R.id.recent_chapter);
            this.tvRepeat = view.findViewById(R.id.recent_repeat);
            this.tvScore = view.findViewById(R.id.recent_score);
            this.tvTotal = view.findViewById(R.id.recent_total);
            this.thumbnail = view.findViewById(R.id.recent_thumbnail);
        }
    }

    public RecentsAdapter(Context ctx, ArrayList<Mark> list) {
        this.mContext = ctx;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recent_marks, viewGroup, false);
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RecentsViewHolder recentsViewHolder = (RecentsViewHolder)holder;
        Mark m = mList.get(position);

        recentsViewHolder.tvTitle.setText(m.getTitle());
        recentsViewHolder.tvDate.setText(Util.dateFormatting(m.getCreatedDate()));
        recentsViewHolder.tvChapter.setText(String.valueOf(m.getChatper()+1));
        recentsViewHolder.tvRepeat.setText(String.valueOf(m.getRepeat()+1));
        recentsViewHolder.tvScore.setText(String.valueOf(m.getScore()));
        recentsViewHolder.tvTotal.setText(String.valueOf(m.getProblemCnt()));

        if(m.getIsUsingThumbNail() > 0){
            Glide.with(mContext)
                    .load(m.getThumbnail())
                    .into(recentsViewHolder.thumbnail);
        }else{
            if(m.getImageName().equals("image_default.PNG")) {
                recentsViewHolder.thumbnail.setImageResource(R.drawable.ic_default);
            }else{
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+m.getImageName());
                Glide.with(mContext)
                        .load(storageReference)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(recentsViewHolder.thumbnail);
            }
        }

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public ArrayList<Mark> getmList(){
        return mList;
    }

}
