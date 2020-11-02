package com.behemoth.repeat.addBook.problem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.recyclerView.chapter.ChapterAdapter;

import java.util.ArrayList;

public class AddProblemActivity extends AppCompatActivity implements AddProblemContract.View, View.OnClickListener {

    private AddProblemContract.Presenter presenter;
    private ChapterAdapter mAdapter;
    private ArrayList<Chapter> mArrayList;
    private Book newBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        setToolbar();

        presenter = new AddProblemPresenter(this);

        setOnClickListener();

        Bundle data = getIntent().getExtras();
        newBook =  data.getParcelable("book");
        Log.d("juntae", "book : " + newBook);

        setRecyclerView();
    }

    private void setOnClickListener(){
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
    }

}