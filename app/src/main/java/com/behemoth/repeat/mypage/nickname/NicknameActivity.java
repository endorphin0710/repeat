package com.behemoth.repeat.mypage.nickname;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.behemoth.repeat.R;
import com.behemoth.repeat.util.Util;

public class NicknameActivity extends AppCompatActivity implements NicknameContract.View, TextWatcher {

    private NicknameContract.Presenter presenter;

    private EditText etNickname;
    private Button btnNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);

        this.presenter = new NicknamePresenter(this);

        initView();
        setToolbar();
    }

    private void initView(){
        btnNickname = findViewById(R.id.btnNickname);
        btnNickname.setOnClickListener(v -> {
            presenter.changeNickname(etNickname.getText().toString().trim());
        });

        etNickname = findViewById(R.id.etNickname);
        etNickname.addTextChangedListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, getString(R.string.nickname_change_fail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length() > 6 || s.length() <= 0){
            makeButtonInactive();
        }else{
            if(Util.containsOnlyKorEngDigit(s.toString())){
                makeButtonActive();
            }else{
                makeButtonInactive();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) { }

    private void makeButtonInactive(){
        btnNickname.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background_gray));
        btnNickname.setTextColor(Color.parseColor("#66000000"));
        btnNickname.setClickable(false);
    }

    private void makeButtonActive(){
        btnNickname.setBackground(ContextCompat.getDrawable(this, R.drawable.button_background_yellow));
        btnNickname.setTextColor(Color.parseColor("#000000"));
        btnNickname.setClickable(true);
    }

}