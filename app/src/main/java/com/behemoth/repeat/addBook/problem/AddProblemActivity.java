package com.behemoth.repeat.addBook.problem;

import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.IntentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.chapter.AddChapterActivity;
import com.behemoth.repeat.addBook.titleAndImage.AddTitleAndImageActivity;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.recyclerView.chapter.ChapterAdapter;

import java.util.ArrayList;

public class AddProblemActivity extends AppCompatActivity implements AddProblemContract.View, View.OnClickListener {

    private AddProblemContract.Presenter presenter;
    private ChapterAdapter mAdapter;
    private ArrayList<Chapter> mArrayList;
    private Book newBook;
    private boolean uploadClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        setToolbar();

        presenter = new AddProblemPresenter(this);

        setOnClickListener();

        Bundle data = getIntent().getExtras();
        newBook =  data.getParcelable("book");

        setRecyclerView();
    }

    private void setOnClickListener(){
        Button btnNext = findViewById(R.id.problemBtnNext);
        btnNext.setOnClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setRecyclerView(){
        RecyclerView mRecyclerView = findViewById(R.id.problemRecyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();

        int cnt = newBook.getChapterCount();
        for(int i = 1; i <= cnt; i++){
            mArrayList.add(new Chapter(i));
        }

        mAdapter = new ChapterAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.problemBtnNext){
            if(uploadClicked) return;
            ArrayList<Chapter> chapters = mAdapter.getmList();
            presenter.saveBookInfo(newBook, chapters);
            uploadClicked = true;
        }
    }

    @Override
    public void onUploadSuccess() {
        Intent i = new Intent(AddProblemActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finishAffinity();
    }
}