package com.behemoth.repeat.addBook.problem;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.recyclerView.chapter.ChapterAdapter;

import java.util.ArrayList;

public class AddProblemActivity extends AppCompatActivity implements AddProblemContract.View {

    private AddProblemContract.Presenter presenter;
    private Book newBook;
    private ChapterAdapter mAdapter;
    private boolean uploadClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        setToolbar();

        presenter = new AddProblemPresenter(this);

        Bundle data = getIntent().getExtras();
        newBook =  data.getParcelable("book");

        setRecyclerView();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void setRecyclerView(){
        RecyclerView mRecyclerView = findViewById(R.id.problemRecyclerView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ArrayList<Chapter> mArrayList = new ArrayList<>();

        int cnt = newBook.getChapterCount();
        for(int i = 1; i <= cnt; i++){
            mArrayList.add(new Chapter(i));
        }
        mArrayList.add(new Chapter(-1));

        mAdapter = new ChapterAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void upload(){
        if(uploadClicked) return;
        ArrayList<Chapter> chapters = mAdapter.getmList();
        presenter.saveBookInfo(newBook, chapters);
        uploadClicked = true;
    }

    @Override
    public void onUploadSuccess() {
        Intent i = new Intent(AddProblemActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finishAffinity();
    }
}