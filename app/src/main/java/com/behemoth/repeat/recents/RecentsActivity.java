package com.behemoth.repeat.recents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.mark.MarkActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);
        setToolbar();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
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

    @Override
    public void onBackPressed() {
        goToMainActivity();
    }

}