package com.behemoth.repeat.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Book> mList;
    private CardClickListener mListener;

    class BookViewHolder extends RecyclerView.ViewHolder {

        protected ConstraintLayout container;
        protected TextView text;
        protected ImageView image;

        public BookViewHolder(View view) {
            super(view);
            this.container = view.findViewById(R.id.card_view);
            this.text = view.findViewById(R.id.bookText);
            this.image = view.findViewById(R.id.imageView);
        }
    }

    class AddViewHolder extends RecyclerView.ViewHolder {

        protected ConstraintLayout container;
        protected ImageView image;

        public AddViewHolder(View view) {
            super(view);
            this.container = view.findViewById(R.id.card_view);
            this.image = view.findViewById(R.id.imageView);
        }
    }

    public BookAdapter(ArrayList<Book> list, CardClickListener listener) {
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.add_card, viewGroup, false);
            return new AddViewHolder(view);
        }else{
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_card, viewGroup, false);
            return new BookViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder.getItemViewType() <= 0){
            AddViewHolder addViewHolder = (AddViewHolder)holder;
            addViewHolder.image.setImageResource(R.drawable.ic_add);
            addViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(position);
                }
            });
        }else{
            BookViewHolder bookViewHolder = (BookViewHolder)holder;
            bookViewHolder.text.setText(mList.get(position).getText());
            bookViewHolder.image.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
            bookViewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
