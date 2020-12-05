package com.behemoth.repeat.mark.chapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.chapter.AddChapterPresenter;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;

import java.util.List;

public class MarkChapterActivity extends AppCompatActivity implements MarkChapterContract.View {

    private MarkChapterContract.Presenter presenter;
    private Book newBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_chapter);

        setToolbar();

        presenter = new MarkChapterPresenter(this);

        Bundle data = getIntent().getExtras();
        newBook = data.getParcelable("markBook");
        getSupportActionBar().setTitle(newBook.getTitle());

        presenter.setRecyclerView(newBook);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public Context getContext() {
        return this;
    }

}