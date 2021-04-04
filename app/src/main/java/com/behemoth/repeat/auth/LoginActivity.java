package com.behemoth.repeat.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.behemoth.repeat.R;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.LogUtil;
import com.behemoth.repeat.util.SharedPreference;
import com.kakao.auth.Session;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Context mContext;
    private boolean ongoing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();

        checkLoginType();
    }

    private void checkLoginType(){
        String loginType = SharedPreference.getInstance().getString(Constants.LOGIN_TYPE, "");
        LogUtil.d(TAG, "loginType: " + loginType);
        switch(loginType){
            case Constants.NAVER :
                OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.showDevelopersLog(true);
                mOAuthLoginModule.init(
                        LoginActivity.this
                        ,mContext.getString(R.string.naver_client_id)
                        ,mContext.getString(R.string.naver_client_secret)
                        ,mContext.getString(R.string.naver_client_name)
                );

                if(mOAuthLoginModule.getState(mContext) == OAuthLoginState.OK){
                    startLogin(loginType);
                }else{
                    prepareButtons();
                }
                break;
            case Constants.KAKAO :
                boolean opened = Session.getCurrentSession().isOpened();
                if(opened){
                    startLogin(loginType);
                }else{
                    prepareButtons();
                }
                break;
            default:
                prepareButtons();
                break;
        }
    }

    private void prepareButtons(){
//        Button naverLoginButton = findViewById(R.id.buttonNaverLogin);
//        naverLoginButton.setVisibility(View.VISIBLE);
//        naverLoginButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if(ongoing) return;
//                startLogin(Constants.NAVER);
//                ongoing = true;
//            }
//        });

        Button kakaoLoginButton = findViewById(R.id.buttonKakaoLogin);
        kakaoLoginButton.setVisibility(View.VISIBLE);
        kakaoLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ongoing) return;
                startLogin(Constants.KAKAO);
                ongoing = true;
            }
        });
    }

    private void startLogin(String name){
        Intent i;
        if(name.equals(Constants.KAKAO)){
            i = new Intent(LoginActivity.this, KakaoLogin.class);
        }
        else {
            i = new Intent(LoginActivity.this, NaverLogin.class);
        }
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
