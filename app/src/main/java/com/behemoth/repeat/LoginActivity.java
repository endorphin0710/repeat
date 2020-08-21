package com.behemoth.repeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Context mContext;

    private static OAuthLogin mOAuthLoginModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        initNaver();
    }

    private void initNaver(){
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.showDevelopersLog(false);
        mOAuthLoginModule.init(
                LoginActivity.this
                ,mContext.getString(R.string.naver_client_id)
                ,mContext.getString(R.string.naver_client_secret)
                ,mContext.getString(R.string.naver_client_name)
        );
        Log.d(TAG, "token:" + mOAuthLoginModule.getAccessToken(mContext));
        NaverLoginHandler naverLoginHandler = new NaverLoginHandler(this);
        //mOAuthLoginModule.startOauthLoginActivity(this, naverLoginHandler);
        OAuthLoginButton mOAuthLoginButton = findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(naverLoginHandler);
    }

    private static class NaverLoginHandler extends OAuthLoginHandler {

        private final WeakReference<LoginActivity> mActivity;

        private NaverLoginHandler(LoginActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run(boolean success) {
            LoginActivity activity = mActivity.get();
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(activity);
                Log.d(TAG, "token:" + accessToken);
                String refreshToken = mOAuthLoginModule.getRefreshToken(activity);
                Log.d(TAG, "refreshToken:" + refreshToken);
                long expiresAt = mOAuthLoginModule.getExpiresAt(activity);
                Log.d(TAG, "expiresAt:" + expiresAt);
                String tokenType = mOAuthLoginModule.getTokenType(activity);
                Log.d(TAG, "tokenType:" + tokenType);
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(activity).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(activity);
                Log.d(TAG, "errorCode:" + errorCode + ", errorDesc:" + errorDesc);
            }
        }
    }




}
