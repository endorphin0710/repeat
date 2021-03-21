package com.behemoth.repeat.recents.markDetail;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.model.Mark;

public class MarkDetailActivity extends AppCompatActivity implements MarkDetailContract.View{

    private MarkDetailContract.Presenter presenter;

    private boolean ascendingQ = false;
    private boolean ascendingM = false;
    private boolean fromStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_detail);

        Intent i = getIntent();
        Mark mark = i.getParcelableExtra("mark");
        this.fromStats = i.getBooleanExtra("fromStats", false);

        initView();
        setToolbar();
        getSupportActionBar().setTitle((mark.getChatper()+1) + "단원 " + (mark.getRepeat()+1) + "회차");

        presenter = new MarkDetailPresenter(this);
        presenter.setRecyclerView();

        presenter.getMarkDetail(mark);
    }

    private void initView(){
        TextView tvProblemNum = findViewById(R.id.tv_question);
        TextView tvMark = findViewById(R.id.tv_mark);

        tvProblemNum.setOnClickListener(v -> {
            presenter.reorderByQuestion(ascendingQ);
            this.ascendingQ = !this.ascendingQ;
        });

        tvMark.setOnClickListener(v -> {
            presenter.reorderByMark(ascendingM);
            this.ascendingM = !this.ascendingM;
        });
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(this.fromStats){
            getMenuInflater().inflate(R.menu.main_menu_with_home, menu);
        }else{
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuHome){
            goToHomeActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void goToHomeActivity(){
        Intent i = new Intent(MarkDetailActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}