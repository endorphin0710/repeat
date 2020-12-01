package com.behemoth.repeat.addBook.SearchBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.model.Search;

public class SearchBookActivity extends AppCompatActivity implements SearchBookContract.View, View.OnClickListener, TextWatcher {

    private SearchBookContract.Presenter presenter;

    private EditText etTitle;
    private ImageView btnRemoveTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        this.presenter = new SearchBookPresenter(this);

        setToolbar();

        setOnClickListener();
        setTextWatcher();
    }

    private void setTextWatcher(){
        etTitle = findViewById(R.id.etTitle);
        etTitle.requestFocus();
        etTitle.addTextChangedListener(this);
    }

    private void setOnClickListener(){
        btnRemoveTitle = findViewById(R.id.btnRemovetitle);
        btnRemoveTitle.setOnClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if(charSequence.length() > 0){
            showRemoveButton();
        }else{
            hideRemoveButton();
        }
        presenter.searchBook(charSequence.toString().trim());
    }

    @Override
    public void afterTextChanged(Editable s) { }

    private void showRemoveButton(){
        btnRemoveTitle.setVisibility(View.VISIBLE);
    }

    private void hideRemoveButton(){
        btnRemoveTitle.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnRemovetitle){
            etTitle.setText("");
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishWithSearchData(Intent i) {
        setResult(RESULT_OK, i);
        finish();
    }
}