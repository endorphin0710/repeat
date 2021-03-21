package com.behemoth.repeat.stats.chapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.recents.markDetail.MarkDetailActivity;

public class ChapterStatsActivity extends AppCompatActivity implements ChapterStatsContract.View, View.OnClickListener{

    private ChapterStatsContract.Presenter presenter;

    private TextView tvChapterStats;
    private TextView tvRepeatStats;
    private boolean seeingStats;

    private String bookId;
    private Chapter chapter;
    private String repeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatper_stats);

        getChapterData();

        initViews();
        setToolbar();

        setChapterStatsFragment();
    }

    private void initViews(){
        tvChapterStats = findViewById(R.id.tv_chapter_stats);
        tvChapterStats.setOnClickListener(this);

        tvRepeatStats = findViewById(R.id.tv_repeat_stats);
        tvRepeatStats.setOnClickListener(this);
    }

    private void getChapterData(){
        bookId = getIntent().getStringExtra("id");
        chapter = getIntent().getParcelableExtra("chapter");
        repeats = getIntent().getStringExtra("repeats");
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        getSupportActionBar().setTitle(chapter.getChapterNumber()+"단원");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_with_home, menu);
        return true;
    }

    private void setChapterStatsFragment(){
        this.seeingStats = true;
        tvChapterStats.setBackgroundColor(getColor(R.color.colorGradientStart));
        tvChapterStats.setTextColor(getColor(R.color.white));

        tvRepeatStats.setBackgroundColor(getColor(R.color.light_gray));
        tvRepeatStats.setTextColor(getColor(R.color.black));

        ChapterStatsFragment chapterStatsFragment = new ChapterStatsFragment();
        Bundle args = new Bundle();
        args.putParcelable("chapter", chapter);
        args.putString("repeats", repeats);
        chapterStatsFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, chapterStatsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setPercentageCorrectAnswersFragment(){
        this.seeingStats = false;
        tvRepeatStats.setBackgroundColor(getColor(R.color.colorGradientStart));
        tvRepeatStats.setTextColor(getColor(R.color.white));

        tvChapterStats.setBackgroundColor(getColor(R.color.light_gray));
        tvChapterStats.setTextColor(getColor(R.color.black));

        PercentageCorrectAnswersFragment percentageCorrectAnswersFragment = new PercentageCorrectAnswersFragment();
        Bundle args = new Bundle();
        args.putString("repeats", repeats);
        percentageCorrectAnswersFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, percentageCorrectAnswersFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_chapter_stats){
            if(!seeingStats) setChapterStatsFragment();
        }else if(id == R.id.tv_repeat_stats){
            if(seeingStats) setPercentageCorrectAnswersFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if(this.seeingStats) {
            finish();
        } else{
            this.seeingStats = true;
            setChapterStatsFragment();
        }
    }

    public void goToMarkDetailActivity(Repeat repeat){
        Intent i = new Intent(ChapterStatsActivity.this, MarkDetailActivity.class);
        Mark m = new Mark();
        m.setId(bookId);
        m.setChatper(chapter.getChapterNumber()-1);
        m.setRepeat(repeat.getRepeatNumber()-1);
        i.putExtra("mark", m);
        i.putExtra("fromStats", true);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}