package com.behemoth.repeat.main;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.recyclerView.bookCard.MainAdapter;
import com.behemoth.repeat.recyclerView.bookCard.CardClickListener;
import com.behemoth.repeat.recyclerView.bookCard.SpaceDecoration;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private final MainContract.View view;
    private final MainContract.Model model;
    private final Context viewContext;

    private ArrayList<Book> mArrayList;
    private MainAdapter mAdapter;

    public MainPresenter(MainContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
        this.model = new MainModel(this);
    }

    @Override
    public void getBooks() {
        model.getBooks();
    }

    @Override
    public void onRetrieveBook(List<Book> books) {
        mArrayList.clear();
        mArrayList.add(new Book(""));
        for(int i = books.size()-1; i >= 0; i--) {
            Book b = books.get(i);
            if(b.getState() == 0){
                mArrayList.add(b);
            }
        }
        mAdapter.notifyDataSetChanged();
        view.hideProgressBar();
    }

    @Override
    public void deleteBook(int position, Book book) {
        model.deleteBook(position, book);
    }

    @Override
    public void onDeleteSuccess(int position) {
        mArrayList.remove(position);
        mAdapter.notifyDataSetChanged();
        view.onDeleteSuccess(position);
    }

    @Override
    public void setRecyclerView() {
        mArrayList = new ArrayList<>();
        RecyclerView mRecyclerView = ((MainActivity)view).findViewById(R.id.mainRecyclerView);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(viewContext, Constants.CARD_COLUMN_COUNT);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        CardClickListener cardClickListener = new CardClickListener() {
            @Override
            public void onClick(int position) {
                onCardClick(position);
            }

            @Override
            public void onMenuClick(int position, Book book) {
                view.showChooseOptions(position, book);
            }
        };

        mAdapter = new MainAdapter(mArrayList, cardClickListener, viewContext);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceDecoration(40));
        mAdapter.notifyDataSetChanged();
    }

    public void onCardClick(int position) {
        if(position == 0){
            view.addNewBook();
        }
    }

}
