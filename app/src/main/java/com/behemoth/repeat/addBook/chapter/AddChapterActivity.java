package com.behemoth.repeat.addBook.chapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.problem.AddProblemActivity;
import com.behemoth.repeat.model.Book;

public class AddChapterActivity extends AppCompatActivity implements AddChapterContract.View, TextWatcher, View.OnClickListener {

    private AddChapterContract.Presenter presenter;
    private EditText etChapter;
    private ImageView btnRemove;
    private Book newBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);

        setToolbar();

        presenter = new AddChapterPresenter(this);

        setTextWatcher();
        setOnClickListener();

        Bundle data = getIntent().getExtras();
        newBook =  data.getParcelable("book");
    }

    private void setOnClickListener(){
        btnRemove = findViewById(R.id.ivRemove);
        btnRemove.setOnClickListener(this);

        Button btnNext = findViewById(R.id.chapterBtnNext);
        btnNext.setOnClickListener(this);
    }

    private void setTextWatcher(){
        etChapter = findViewById(R.id.etChapter);
        etChapter.addTextChangedListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
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
        }else if(id == R.id.chapterBtnNext){
            String chapter = etChapter.getText().toString().trim();
            presenter.validateInput(chapter, newBook);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(String message) {
        Log.d("juntae", "message : " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void nextStep(Book book) {
        Intent i = new Intent(AddChapterActivity.this, AddProblemActivity.class);
        i.putExtra("book", book);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}