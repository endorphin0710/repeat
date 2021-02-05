package com.behemoth.repeat.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.titleAndImage.AddTitleAndImageActivity;
import com.behemoth.repeat.mark.MarkActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.LogUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;
    private LottieAnimationView progressBar;
    private ConstraintLayout loadingLayout;
    private BottomNavigationView bottomNavigationView;

    private static final long TIME_INTERVAL = 2000L;
    private int dataChanged = -1;
    private long time_back_button_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        initViews();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.bottom_navigation_mark){
                goToMarkActivity();
                return false;
            }else if(id == R.id.bottom_navigation_stats){
                return false;
            }
            return false;
        });

        presenter = new MainPresenter(this);
        presenter.setRecyclerView();

        getBooks();
    }

    private void initViews(){
        loadingLayout = findViewById(R.id.loading_layout);
        loadingLayout.bringToFront();

        progressBar = findViewById(R.id.mainProgressBar);
        progressBar.setRepeatCount(LottieDrawable.INFINITE);
        progressBar.setRepeatMode(LottieDrawable.RESTART);
    }

    private void getBooks(){
        presenter.getBooks();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_profile);
    }

    @Override
    public void showChooseOptions(int position, Book book){
        AlertDialog.Builder optionBuilder = new AlertDialog.Builder(this, R.style.dialogTheme);

        String[] book_menu = getResources().getStringArray(R.array.book_menu);
        optionBuilder.setTitle(book.getTitle());
        optionBuilder.setItems(book_menu, (dialog, which) -> {
            switch(which){
                case 0:
                    updateTitleAndImage(book);
                    break;
                case 1:
                    break;
                case 2:
                    AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this,  R.style.dialogTheme);
                    confirmBuilder.setMessage(getString(R.string.confirm_delete));
                    confirmBuilder.setPositiveButton(getString(R.string.delete), (dialog1, which1) -> presenter.deleteBook(position, book));
                    confirmBuilder.setNegativeButton(getString(R.string.cancel), (dialog2, which2) -> { });
                    confirmBuilder.create().show();
                    break;
                default:
                    break;
            }
        });
        optionBuilder.setNegativeButton(getString(R.string.cancel), (dialog2, which2) -> { });

        optionBuilder.create().show();
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
    public void updateTitleAndImage(Book book) {
        Intent i = new Intent(MainActivity.this, AddTitleAndImageActivity.class);
        i.putExtra("change", true);
        i.putExtra("book", book);
        startActivityForResult(i, Constants.REQUEST_RELOAD, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void addNewBook() {
        Intent i = new Intent(MainActivity.this, AddTitleAndImageActivity.class);
        i.putExtra("change", false);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void goToMarkActivity(){
        Intent i = new Intent(MainActivity.this, MarkActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.putExtra("dataChanged", dataChanged);
        dataChanged = 0;
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dataChanged = intent.getIntExtra("dataChanged", 0);
        if(dataChanged > 0) {
            getBooks();
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if(t - time_back_button_pressed < TIME_INTERVAL){
            finishAffinity();
        }else{
            time_back_button_pressed = t;
            Toast.makeText(this, getString(R.string.confirm_finish), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgressBar(){
        getSupportActionBar().hide();
        bottomNavigationView.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
        progressBar.playAnimation();
    }

    @Override
    public void hideProgressBar(){
        getSupportActionBar().show();
        bottomNavigationView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
        progressBar.pauseAnimation();
    }

    @Override
    public void onDeleteSuccess(int position) {
        dataChanged = 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_RELOAD && resultCode == RESULT_OK ) onNewIntent(data);
    }

}
