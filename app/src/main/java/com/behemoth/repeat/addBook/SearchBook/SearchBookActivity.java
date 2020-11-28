package com.behemoth.repeat.addBook.SearchBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.behemoth.repeat.R;

public class SearchBookActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText etTitle;
    private ImageView btnRemoveTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        setToolbar();

        setOnClickListener();
        setTextWatcher();
    }

    private void setTextWatcher(){
        etTitle = findViewById(R.id.etTitle);
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
}