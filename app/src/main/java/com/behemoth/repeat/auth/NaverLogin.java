package com.behemoth.repeat.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.behemoth.repeat.MainActivity;
import com.behemoth.repeat.R;
import com.behemoth.repeat.tasks.RetrieveNaverUserInfo;
import com.behemoth.repeat.util.ExecutorUtil;
import com.behemoth.repeat.util.LogUtil;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class NaverLogin extends AppCompatActivity {

    private static final String TAG = "NaverLogin";
    private Context mContext;

    private static OAuthLogin mOAuthLoginModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        mContext = getApplicationContext();
        initNaver();
    }

    private void initNaver(){
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.showDevelopersLog(true);
        mOAuthLoginModule.init(
                NaverLogin.this
                ,mContext.getString(R.string.naver_client_id)
                ,mContext.getString(R.string.naver_client_secret)
                ,mContext.getString(R.string.naver_client_name)
        );

        if(mOAuthLoginModule.getState(mContext) == OAuthLoginState.OK){
            LogUtil.d(TAG, "LoginState: OK");
            mOAuthLoginModule.refreshAccessToken(mContext);
            String token = mOAuthLoginModule.getAccessToken(mContext);
            ExecutorUtil.getInstance().execute(new RetrieveNaverUserInfo(token));

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            mOAuthLoginModule.startOauthLoginActivity(this, new NaverLoginHandler(this, mContext));
        }
    }


    private static class NaverLoginHandler extends OAuthLoginHandler {

        private final Activity parent;
        private final Context ctx;

        private NaverLoginHandler(Activity activity, Context ctx){
            this.parent = activity;
            this.ctx = ctx;
        }

        @Override
        public void run(boolean success) {
            if (success) {
                LogUtil.d(TAG, "login succeeded[NAVER]");
                mOAuthLoginModule.refreshAccessToken(ctx);
                String token = mOAuthLoginModule.getAccessToken(ctx);
                ExecutorUtil.getInstance().execute(new RetrieveNaverUserInfo(token));

                Intent i = new Intent(parent, MainActivity.class);
                parent.startActivity(i);
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(ctx).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(ctx);
                LogUtil.d(TAG, "errorCode:" + errorCode + ", errorDesc:" + errorDesc);

                Intent i = new Intent(parent, LoginActivity.class);
                parent.startActivity(i);
            }
            parent.finish();
        }

    }

}
