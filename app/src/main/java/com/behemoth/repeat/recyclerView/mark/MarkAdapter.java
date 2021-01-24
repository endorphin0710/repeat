package com.behemoth.repeat.recyclerView.mark;

import android.content.Context;
import android.util.Log;
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
import com.behemoth.repeat.recyclerView.bookCard.CardClickListener;
import com.behemoth.repeat.util.Util;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Book> mList;
    private CardClickListener mListener;
    private Context mContext;

    class BookViewHolder extends RecyclerView.ViewHolder {

        protected ConstraintLayout container;
        protected TextView text;
        protected ImageView image;
        protected TextView date;
        protected ImageView bookMenu;

        public BookViewHolder(View view) {
            super(view);
            this.container = view.findViewById(R.id.card_view);
            this.text = view.findViewById(R.id.bookText);
            this.date = view.findViewById(R.id.tvCreatedDate);
            this.image = view.findViewById(R.id.imageView);
            this.bookMenu = view.findViewById(R.id.bookMenu);
        }
    }

    public MarkAdapter(ArrayList<Book> list, CardClickListener listener, Context ctx) {
        this.mList = list;
        this.mListener = listener;
        this.mContext = ctx;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.book_card, viewGroup, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        BookViewHolder bookViewHolder = (BookViewHolder)holder;
        Book book = mList.get(position);

        bookViewHolder.container.setOnClickListener(view -> {
            mListener.onClick(position);
        });

        bookViewHolder.text.setText(book.getTitle());
        bookViewHolder.date.setText(Util.dateFormatting(book.getCreatedDate()));
        bookViewHolder.bookMenu.setVisibility(View.GONE);

        if(book.getIsUsingThumbnail() > 0){
            String thumbnailUrl = book.getThumbnail();
            if(thumbnailUrl.length() > 0){
                Glide.with(mContext)
                        .load(thumbnailUrl)
                        .into(bookViewHolder.image);
            }else{
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/image_default.PNG");;
                Glide.with(mContext)
                        .load(storageReference)
                        .into(bookViewHolder.image);
            }
        }else{
            String imageName = book.getImageName();
            if(imageName.equals("image_default.PNG")){
                bookViewHolder.image.setImageResource(R.drawable.ic_default);
            }else{
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+book.getImageName());;
                Glide.with(mContext)
                        .load(storageReference)
                        .into(bookViewHolder.image);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
