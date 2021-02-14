package com.behemoth.repeat.mark.repeat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.problem.AddProblemActivity;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.mark.MarkActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Problem;
import com.behemoth.repeat.util.SharedPreference;

import java.util.List;

public class MarkRepeatActivity extends AppCompatActivity implements MarkRepeatContract.View {

    private MarkRepeatContract.Presenter presenter;
    private Book book;
    private int chapterNumber;
    private LottieAnimationView progressBar;
    private ConstraintLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_repeat);

        presenter = new MarkRepeatPresenter(this);

        Bundle data = getIntent().getExtras();
        setToolbar(data);
        initView();
    }

    private void initView(){
        progressBar = findViewById(R.id.markProgressBar);
        progressBar.setRepeatCount(LottieDrawable.INFINITE);
        progressBar.setRepeatMode(LottieDrawable.RESTART);

        loadingLayout = findViewById(R.id.loading_layout_mark);
        loadingLayout.bringToFront();
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
        showProgressBar();
        presenter.mark(book, chapterNumber, problems);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onUpdateSuccess() {
        SharedPreference.getInstance().onDataChanged();
        Intent i = new Intent(MarkRepeatActivity.this, MarkActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onUpdateFailure() {
        hideProgressBar();
        Toast.makeText(this, getString(R.string.text_fail_mark), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(){
        getSupportActionBar().hide();
        loadingLayout.setVisibility(View.VISIBLE);
        progressBar.playAnimation();
    }

    @Override
    public void hideProgressBar(){
        getSupportActionBar().show();
        loadingLayout.setVisibility(View.GONE);
        progressBar.pauseAnimation();
    }

}