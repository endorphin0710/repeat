package com.behemoth.repeat.mark.repeat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.problem.AddProblemActivity;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.mark.MarkActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Problem;

import java.util.List;

public class MarkRepeatActivity extends AppCompatActivity implements MarkRepeatContract.View {

    private MarkRepeatContract.Presenter presenter;
    private Book book;
    private int chapterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_repeat);

        presenter = new MarkRepeatPresenter(this);

        Bundle data = getIntent().getExtras();
        setToolbar(data);
    }

    private void setToolbar(Bundle data){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        book = data.getParcelable("markBook");
        chapterNumber = data.getInt("chapterNumber");
        getSupportActionBar().setTitle((chapterNumber+1)+"단원");

        presenter.setRecyclerView(book, chapterNumber);
    }

    public void mark(List<Problem> problems){
        presenter.mark(book, chapterNumber, problems);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onUpdateSuccess() {
        Intent i = new Intent(MarkRepeatActivity.this, MarkActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}