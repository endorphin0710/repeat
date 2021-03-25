package com.behemoth.repeat.mypage.terms;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.recyclerView.mypage.ArticleAdapter;

import java.util.ArrayList;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        setToolbar();
        setRecyclerView();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void setRecyclerView(){
        RecyclerView mRecyclerView = findViewById(R.id.terms_recyclerview);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        String[] articls = getResources().getStringArray(R.array.articles);
        ArrayList<String> data = new ArrayList<>();
        for(int i = 0; i < articls.length; i++){
            data.add(articls[i]);
        }

        ArticleAdapter adapter = new ArticleAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
    }

}