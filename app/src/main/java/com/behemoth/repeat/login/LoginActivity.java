package com.behemoth.repeat.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.behemoth.repeat.R;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.LogUtil;
import com.behemoth.repeat.util.SharedPreference;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();

        checkLoginType();
    }

    private void checkLoginType(){
        String loginType = SharedPreference.getInstance().getString(Constants.LOGIN_TYPE, "");
        LogUtil.d(TAG, "loginType: " + loginType);
        if(loginType.equals(Constants.NAVER)){
            startNaverLogin();
        }else{
            prepareButtons();
        }
    }

    private void prepareButtons(){
        Button mOAuthLoginButton = findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setVisibility(View.VISIBLE);
        mOAuthLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startNaverLogin();
            }
        });
    }

    private void startNaverLogin(){
        Intent i = new Intent(LoginActivity.this, NaverLogin.class);
        startActivity(i);
        finish();
    }

}
