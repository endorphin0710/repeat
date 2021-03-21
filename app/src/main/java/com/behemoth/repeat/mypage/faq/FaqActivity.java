package com.behemoth.repeat.mypage.faq;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.FAQ;
import com.behemoth.repeat.recyclerView.mypage.FaqAdapter;

import java.util.ArrayList;

public class FaqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

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
        RecyclerView mRecyclerView = findViewById(R.id.faq_recyclerview);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        String[] questions = getResources().getStringArray(R.array.faq_questions);
        String[] answers = getResources().getStringArray(R.array.faq_answers);
        ArrayList<FAQ> data = new ArrayList<>();
        for(int i = 0; i < questions.length; i++){
            data.add(new FAQ(questions[i], answers[i]));
        }

        FaqAdapter adapter = new FaqAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
    }

}