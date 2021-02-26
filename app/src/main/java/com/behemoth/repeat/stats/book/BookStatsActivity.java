package com.behemoth.repeat.stats.book;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.stats.chapter.ChapterStatsActivity;
import com.google.gson.Gson;

public class BookStatsActivity extends AppCompatActivity implements BookStatsContract.View, View.OnClickListener {

    private BookStatsContract.Presenter presenter;

    private Book book;

    private TextView tvBookStats;
    private TextView tvChapterStats;

    private boolean seeingStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_stats);

        book = getIntent().getParcelableExtra("book");
        presenter = new BookStatsPresenter(this);

        initViews();
        setToolbar();

        presenter.getBookInfo(book.getId());
    }

    private void initViews(){
        tvBookStats = findViewById(R.id.tv_book_stats);
        tvBookStats.setOnClickListener(this);

        tvChapterStats = findViewById(R.id.tv_chapter_stats);
        tvChapterStats.setOnClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        getSupportActionBar().setTitle(book.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void setBookStatsFragment(){
        this.seeingStats = true;
        tvBookStats.setBackgroundColor(getColor(R.color.colorGradientStart));
        tvBookStats.setTextColor(getColor(R.color.white));

        tvChapterStats.setBackgroundColor(getColor(R.color.light_gray));
        tvChapterStats.setTextColor(getColor(R.color.black));

        BookStatsFragment bookStatsFragment = new BookStatsFragment();
        Bundle args = new Bundle();
        args.putParcelable("book", book);
        bookStatsFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, bookStatsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setChapterListFragment(){
        this.seeingStats = false;
        tvChapterStats.setBackgroundColor(getColor(R.color.colorGradientStart));
        tvChapterStats.setTextColor(getColor(R.color.white));

        tvBookStats.setBackgroundColor(getColor(R.color.light_gray));
        tvBookStats.setTextColor(getColor(R.color.black));

        ChapterListFragment chapterListFragment = new ChapterListFragment();
        Bundle args = new Bundle();
        args.putInt("chapter_count", book.getChapterCount());
        chapterListFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, chapterListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onRetrieveBook(Book b) {
        this.book = b;
        this.seeingStats = true;
        setBookStatsFragment();
    }

    @Override
    public void onBackPressed() {
        if(this.seeingStats) {
            finish();
        } else{
            setBookStatsFragment();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_chapter_stats){
            if(seeingStats) {
                setChapterListFragment();
            }
        }else if(id == R.id.tv_book_stats){
            if(!seeingStats) {
                setBookStatsFragment();
            }
        }
    }

    public void goToChapterStatsActivity(int position){
        Chapter c = book.getChapter().get(position);

        Gson gson = new Gson();
        String repeats = gson.toJson(c.getRepeat());

        Intent i = new Intent(BookStatsActivity.this, ChapterStatsActivity.class);
        i.putExtra("id", book.getId());
        i.putExtra("chapter", c);
        i.putExtra("repeats", repeats);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}