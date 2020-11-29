package com.behemoth.repeat.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.titleAndImage.AddTitleAndImageActivity;
import com.behemoth.repeat.mark.MarkActivity;
import com.behemoth.repeat.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getBooks();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_profile);
    }

    @Override
    public void showChooseOptions(int position, Book book){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_MaterialComponents_Light_Dialog);

        String[] book_menu = getResources().getStringArray(R.array.book_menu);
        builder.setItems(book_menu, (dialog, which) -> {
            if(which == 0){
                presenter.deleteBook(position, book);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
    public void addNewBook() {
        Intent i = new Intent(MainActivity.this, AddTitleAndImageActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void goToMarkActivity(){
        Intent i = new Intent(MainActivity.this, MarkActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}
