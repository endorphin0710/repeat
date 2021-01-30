package com.behemoth.repeat.mark;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.recyclerView.bookCard.CardClickListener;
import com.behemoth.repeat.recyclerView.mark.MarkAdapter;
import com.behemoth.repeat.recyclerView.bookCard.SpaceDecoration;
import com.behemoth.repeat.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class MarkPresenter implements MarkContract.Presenter{

    private final MarkContract.View view;
    private final MarkContract.Model model;
    private final Context viewContext;

    private ArrayList<Book> mArrayList;
    private MarkAdapter mAdapter;

    public MarkPresenter(MarkContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new MarkModel(this);
    }

    @Override
    public void getBooks() {
        view.showProgressBar();
        model.getBooks();
    }

    @Override
    public void onRetrieveBook(List<Book> books) {
        mArrayList.clear();
        for(int i = books.size()-1; i >= 0; i--) {
            Book b = books.get(i);
            if(b.getState() == 0) {
                mArrayList.add(new Book(b.getId(), b.getAuthor(), b.getTitle(), b.getImageName(), b.getThumbnail(), b.getIsUsingThumbnail(), b.getCreatedDate(), b.getChapter()));
            }
        }
        mAdapter.notifyDataSetChanged();
        view.hideProgressBar();
    }

    @Override
    public void setRecyclerView() {
        mArrayList = new ArrayList<>();
        RecyclerView mRecyclerView = ((MarkActivity)view).findViewById(R.id.markRecyclerView);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(viewContext, Constants.CARD_COLUMN_COUNT);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        CardClickListener cardClickListener = new CardClickListener() {
            @Override
            public void onClick(int position) {
                onCardClick(position);
            }

            @Override
            public void onMenuClick(int position, Book book) { }
        };

        mAdapter = new MarkAdapter(mArrayList, cardClickListener, viewContext);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceDecoration(40));
        mAdapter.notifyDataSetChanged();
    }

    public void onCardClick(int position) {
        Book b = mArrayList.get(position);
        view.goToMarkChapterActivity(b);
    }

}
