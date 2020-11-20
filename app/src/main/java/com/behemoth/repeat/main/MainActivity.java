package com.behemoth.repeat.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.titleAndImage.AddTitleAndImageActivity;
import com.behemoth.repeat.recyclerView.card.BookAdapter;
import com.behemoth.repeat.recyclerView.card.CardClickListener;
import com.behemoth.repeat.recyclerView.card.SpaceDecoration;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ArrayList<Book> mArrayList;
    private BookAdapter mAdapter;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        presenter = new MainPresenter(this);

        setRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getBooks();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_profile);
    }

    private void setRecyclerView(){
        mArrayList = new ArrayList<>();
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, Constants.CARD_COLUMN_COUNT);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        CardClickListener cardClickListener = new CardClickListener() {
            @Override
            public void onClick(int position) {
                presenter.onClick(position);
            }

            @Override
            public void onMenuClick(int position, Book book) {
                showChooseOptions(position, book);
            }
        };

        mAdapter = new BookAdapter(mArrayList, cardClickListener, this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceDecoration(20));
        mAdapter.notifyDataSetChanged();

    }

    private void showChooseOptions(int position, Book book){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Light_Dialog);

        String[] book_menu = getResources().getStringArray(R.array.book_menu);
        builder.setItems(book_menu, (dialog, which) -> {
            if(which == 0){
                presenter.deleteBook(position, book);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void newBook() {
        Intent i = new Intent(MainActivity.this, AddTitleAndImageActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onRetrieveBook(List<Book> books) {
        mArrayList.clear();
        mArrayList.add(new Book(""));
        for(Book b : books){
            mArrayList.add(new Book(b.getId(), b.getAuthor(), b.getTitle(), b.getImageName(), b.getCreatedDate()));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteSuccess(int position) {
        mArrayList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

}
