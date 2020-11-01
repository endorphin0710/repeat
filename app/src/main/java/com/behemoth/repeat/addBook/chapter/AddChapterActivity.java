package com.behemoth.repeat.addBook.chapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.behemoth.repeat.R;

public class AddChapterActivity extends AppCompatActivity implements AddChapterContract.View, TextWatcher, View.OnClickListener {

    private AddChapterContract.Presenter presenter;

    private EditText etChapter;
    private ImageView btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);

        setToolbar();

        presenter = new AddChapterPresenter(this);

        setTextWatcher();
        setOnClickListener();
    }

    private void setOnClickListener(){
        btnRemove = findViewById(R.id.ivRemove);
        btnRemove.setOnClickListener(this);
    }

    private void setTextWatcher(){
        btnRemove = findViewById(R.id.ivRemove);
        etChapter = findViewById(R.id.etChapter);
        etChapter.addTextChangedListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        if(charSequence.length() > 0){
            showRemoveButton();
        }else{
            hideRemoveButton();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void showRemoveButton(){
        btnRemove.setVisibility(View.VISIBLE);
    }

    private void hideRemoveButton(){
        btnRemove.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.ivRemove){
            etChapter.setText("");
        }
    }
}