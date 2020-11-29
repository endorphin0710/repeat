package com.behemoth.repeat.mark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MarkActivity extends AppCompatActivity implements MarkContract.View{

    private MarkContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        setToolbar();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_mark);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.bottom_navigation_home){
                onBackPressed();
                return false;
            }else if(id == R.id.bottom_navigation_stats){
                return false;
            }
            return false;
        });

        this.presenter = new MarkPresenter(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }
}