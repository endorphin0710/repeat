package com.behemoth.repeat.recents;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.mark.MarkActivity;
import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.mypage.MyPageActivity;
import com.behemoth.repeat.recents.markDetail.MarkDetailActivity;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecentsActivity extends AppCompatActivity implements RecentsContract.View {

    private RecentsContract.Presenter presenter;

    private BottomNavigationView bottomNavigationView;
    private LottieAnimationView progressBar;
    private ConstraintLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);

        setToolbar();
        initViews();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_recents);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.bottom_navigation_home){
                goToMainActivity();
                return false;
            }else if(id == R.id.bottom_navigation_mark){
                goToMarkActivity();
                return false;
            }
            return false;
        });

        presenter = new RecentsPresenter(this);
        presenter.setRecyclerView();

        getRecentMarks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int dataChanged = SharedPreference.getInstance().getRefresh(Constants.DATA_CHANGED, 0);
        int recentsRefreshed = SharedPreference.getInstance().getRefresh(Constants.REFRESH_RECENTS, 0);
        if(dataChanged > 0 && recentsRefreshed == 0) {
            SharedPreference.getInstance().setRefresh(Constants.REFRESH_RECENTS, 1);
            getRecentMarks();
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void initViews(){
        loadingLayout = findViewById(R.id.loading_layout_recents);
        loadingLayout.bringToFront();

        progressBar = findViewById(R.id.recents_progresbar);
        progressBar.setRepeatCount(LottieDrawable.INFINITE);
        progressBar.setRepeatMode(LottieDrawable.RESTART);
    }

    @Override
    public void getRecentMarks(){
        this.showProgressBar();
        presenter.getRecentMarks();
    }

    @Override
    public void showProgressBar() {
        getSupportActionBar().hide();
        bottomNavigationView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
        progressBar.playAnimation();
    }

    @Override
    public void hideProgressBar() {
        getSupportActionBar().show();
        bottomNavigationView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        progressBar.pauseAnimation();
    }

    @Override
    public void viewMarkDetail(Mark m) {
        Intent i = new Intent(RecentsActivity.this, MarkDetailActivity.class);
        i.putExtra("mark", m);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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

    private void goToMainActivity(){
        Intent i = new Intent(RecentsActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void goToMarkActivity(){
        Intent i = new Intent(RecentsActivity.this, MarkActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void goToMyPage(){
        Intent i = new Intent(RecentsActivity.this, MyPageActivity.class);
        startActivityForResult(i, Constants.REQUEST_MYPAGE, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_MYPAGE && resultCode == RESULT_OK) {
            SharedPreference.getInstance().onDataChanged();
            SharedPreference.getInstance().setRefresh(Constants.REFRESH_RECENTS, 1);
            getRecentMarks();
        }
    }
}