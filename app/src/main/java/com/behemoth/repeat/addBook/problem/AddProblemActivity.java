package com.behemoth.repeat.addBook.problem;

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
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.SharedPreference;

public class AddProblemActivity extends AppCompatActivity implements AddProblemContract.View {

    private AddProblemContract.Presenter presenter;
    private LottieAnimationView progressBar;
    private ConstraintLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        setToolbar();

        presenter = new AddProblemPresenter(this);

        Bundle data = getIntent().getExtras();
        Book newBook = data.getParcelable("book");

        presenter.setRecyclerView(newBook);

        initView();
    }

    private void initView(){
        progressBar = findViewById(R.id.add_book_progressbar);
        progressBar.setRepeatCount(LottieDrawable.INFINITE);
        progressBar.setRepeatMode(LottieDrawable.RESTART);

        loadingLayout = findViewById(R.id.loading_layout_add_book);
        loadingLayout.bringToFront();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    public void upload() {
        showProgressBar();
        presenter.upload();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onUploadSuccess()  {
        SharedPreference.getInstance().onDataChanged();
        Intent i = new Intent(AddProblemActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putExtra("state", "add");
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onUploadFailure() {
        hideProgressBar();
        Toast.makeText(this, getString(R.string.text_fail_add_book), Toast.LENGTH_SHORT).show();
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