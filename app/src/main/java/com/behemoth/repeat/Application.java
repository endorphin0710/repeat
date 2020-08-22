package com.behemoth.repeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behemoth.repeat.auth.NaverLogin;
import com.behemoth.repeat.util.SharedPreference;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreference.init(getApplicationContext());

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                if(activity.getClass().getSimpleName().equals("LoginActivity")
                        || activity.getClass().getSimpleName().equals("OAuthLoginActivity")
                        || activity.getClass().getSimpleName().equals("NaverLogin")) return;
                Log.d("juntae", "onActivity Resumed");
                OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.showDevelopersLog(true);
                mOAuthLoginModule.init(
                        getApplicationContext()
                        ,getApplicationContext().getString(R.string.naver_client_id)
                        ,getApplicationContext().getString(R.string.naver_client_secret)
                        ,getApplicationContext().getString(R.string.naver_client_name)
                );

                if(mOAuthLoginModule.getState(getApplicationContext()) != OAuthLoginState.OK){
                    Log.d("juntae", "onActivity Resumed : state NOT OK");
                    startNaverLogin();
                }else{
                    Log.d("juntae", "onActivity Resumed : state OK");
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    private void startNaverLogin(){
        Intent i = new Intent(getApplicationContext(), NaverLogin.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
