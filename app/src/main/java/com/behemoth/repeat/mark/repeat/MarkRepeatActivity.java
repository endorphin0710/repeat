package com.behemoth.repeat.mark.repeat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.behemoth.repeat.R;
import com.behemoth.repeat.mark.MarkActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
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
        initView();
        setToolbar(data);
        presenter.setRecyclerView(chapterNumber);
    }

    private void initView(){
        progressBar = findViewById(R.id.markProgressBar);
        progressBar.setRepeatCount(LottieDrawable.INFINITE);
        progressBar.setRepeatMode(LottieDrawable.RESTART);

        loadingLayout = findViewById(R.id.loading_layout_mark);
        loadingLayout.bringToFront();

        RadioGroup rg = findViewById(R.id.radioGroupAll);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case R.id.radioAllCorrect:
                    presenter.allCorrect();
                    break;
                case R.id.radioAllWrong:
                    presenter.allWrong();
                    break;
                case R.id.radioAllReset:
                    presenter.allReset();
                    break;
            }
        });
    }

    private void setToolbar(Bundle data){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        book = data.getParcelable("markBook");
        List<Chapter> chapters = book.getChapter();
        chapterNumber = data.getInt("chapterNumber");
        getSupportActionBar().setTitle((chapterNumber+1)+"단원");

        presenter.getBook(book.getId());
    }

    public void mark(List<Problem> problems, boolean temp){
        showProgressBar();
        presenter.mark(book, chapterNumber, problems, temp);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onUpdateSuccess(boolean goToRecents) {
        SharedPreference.getInstance().onDataChanged();
        Intent i = new Intent(MarkRepeatActivity.this, MarkActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(goToRecents) i.putExtra("mark_completed", true);
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

    @Override
    public void onRetrieveBook(Book b) {
        RadioGroup rg = findViewById(R.id.radioGroupAll);
        rg.setVisibility(View.VISIBLE);
        this.book = b;
    }

}