package com.behemoth.repeat.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.titleAndImage.AddTitleAndImageActivity;
import com.behemoth.repeat.recyclerView.BookAdapter;
import com.behemoth.repeat.recyclerView.CardClickListener;
import com.behemoth.repeat.recyclerView.SpaceDecoration;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ArrayList<Book> mArrayList;
    private BookAdapter mAdapter;
    private CardClickListener cardClickListener;
    private int count = -1;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        presenter = new MainPresenter(this);
        setRecyclerView();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_profile);
    }

    private void setRecyclerView(){
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, Constants.CARD_COLUMN_COUNT);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mArrayList = new ArrayList<>();
        cardClickListener = new CardClickListener() {
            @Override
            public void onClick(int position) {
                presenter.onClick(position);
            }
        };

        /** dummy data for index 0 **/
        mArrayList.add(new Book(""));

        List<Book> books = presenter.getBooks();
        for(Book b : books){
            mArrayList.add(new Book(""));
        }

        mAdapter = new BookAdapter(mArrayList, cardClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceDecoration(20));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void newBook() {
        Intent i = new Intent(MainActivity.this, AddTitleAndImageActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
