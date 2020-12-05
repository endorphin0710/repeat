package com.behemoth.repeat.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.R;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.LogUtil;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

public class KakaoLogin extends AppCompatActivity {

    private static final String TAG = "KakaoLogin";
    private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            LogUtil.d(TAG, "onSessionOpened: ");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            LogUtil.d(TAG, "onSessionOpenFailed: ");
            if (exception != null) {
                LogUtil.d(TAG, "exception : " + exception);
            }
        }
    }

    private void requestMe() {
        LogUtil.d(TAG, "requestMe: ");
        List<String> keys = new ArrayList<>();

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                LogUtil.d(TAG, "failed to get user info. msg=" + errorResult);
                startLoginActivity();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                startLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response response) {
                String token = Session.getCurrentSession().getTokenInfo().getAccessToken();
                LogUtil.d(TAG, "token -> " + token);

                LogUtil.d(TAG, "reuqestMe -> onSuccess: ");
                String id = Long.toString(response.getId());
                saveUser(id);
                startMainActivity();
            }
        });
    }

    private void saveUser(String id){
        id = Constants.KAKAO_ID_PREFIX + id;

        /** firebase **/
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("user").child(id).setValue(new User(id, Constants.USER_TYPE_SOCIAL));

        /** sharedPreference **/
        SharedPreference.getInstance().putString(Constants.LOGIN_TYPE, Constants.KAKAO);
        SharedPreference.getInstance().putString(Constants.USER_ID, id);
    }

    private void startLoginActivity(){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

    private void startMainActivity(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

}