package com.behemoth.repeat.mark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.mark.chapter.MarkChapterActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.mypage.MyPageActivity;
import com.behemoth.repeat.recents.RecentsActivity;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MarkActivity extends AppCompatActivity implements MarkContract.View{

    private MarkContract.Presenter presenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        setToolbar();

        initViews();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_mark);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.bottom_navigation_home){
                goToMainActivity();
                return false;
            }else if(id == R.id.bottom_navigation_recents){
                goToRecentsActivity();
                return false;
            }
            return false;
        });

        this.presenter = new MarkPresenter(this);
        presenter.setRecyclerView();

        getBooks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int dataChanged = SharedPreference.getInstance().getRefresh(Constants.DATA_CHANGED, 0);
        int markRefreshed = SharedPreference.getInstance().getRefresh(Constants.REFRESH_MARK, 0);
        if(dataChanged > 0 && markRefreshed == 0) {
            SharedPreference.getInstance().setRefresh(Constants.REFRESH_MARK, 1);
            getBooks();
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void initViews(){
        progressBar = findViewById(R.id.markProgressBar);
    }

    private void getBooks(){
        presenter.getBooks();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_profile);
        toolbar.setNavigationOnClickListener(view -> {
            goToMyPage();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void goToMarkChapterActivity(Book b) {
        Intent i = new Intent(MarkActivity.this, MarkChapterActivity.class);
        i.putExtra("markBook", b);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    private void goToMainActivity(){
        Intent i = new Intent(MarkActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void goToRecentsActivity(){
        Intent i = new Intent(MarkActivity.this, RecentsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void goToMyPage(){
        Intent i = new Intent(MarkActivity.this, MyPageActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
    }

}