package com.behemoth.repeat.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behemoth.repeat.R;
import com.behemoth.repeat.auth.LoginActivity;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.LogUtil;
import com.behemoth.repeat.util.SharedPreference;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class Application extends android.app.Application {

    private static final String TAG = "Application";
    private static Application instance;

    public static Application getGlobalApplicationContext() {
        if (instance == null) {
            throw new IllegalStateException("This Application does not inherit com.kakao.GlobalApplication");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreference.init(getApplicationContext());
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());

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
                        || activity.getClass().getSimpleName().equals("NaverLogin")
                        || activity.getClass().getSimpleName().equals("KakaoLogin")) return;
                LogUtil.d(TAG, "onActivity Resumed");

                String loginType = SharedPreference.getInstance().getString(Constants.LOGIN_TYPE, "");
                switch(loginType){
                    case Constants.NAVER :
                        OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance();
                        mOAuthLoginModule.showDevelopersLog(true);
                        mOAuthLoginModule.init(
                                getApplicationContext()
                                ,getApplicationContext().getString(R.string.naver_client_id)
                                ,getApplicationContext().getString(R.string.naver_client_secret)
                                ,getApplicationContext().getString(R.string.naver_client_name)
                        );

                        if(mOAuthLoginModule.getState(getApplicationContext()) != OAuthLoginState.OK){
                            LogUtil.d(TAG, "onActivity Resumed : NAVER OAuthLoginState NOT OK");
                            startLoginActivity();
                        }
                        break;
                    case Constants.KAKAO :
                        boolean opened = Session.getCurrentSession().isOpened();
                        if(!opened){
                            LogUtil.d(TAG,"onActivity Resumed : KAKAO SESSION CLOSED");
                            startLoginActivity();
                        }
                        break;
                    default:
                        break;
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

    private void startLoginActivity(){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private static class KakaoSDKAdapter extends KakaoAdapter {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         *
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return Application.getGlobalApplicationContext();
                }
            };
        }
    }

}
