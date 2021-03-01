package com.behemoth.repeat.auth;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.behemoth.repeat.R;
import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.model.User;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.LogUtil;
import com.behemoth.repeat.util.SharedPreference;
import com.behemoth.repeat.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        setContentView(R.layout.activity_splash);

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
            startLoginActivity();
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

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInAnonymously()
                        .addOnSuccessListener(authResult -> {
                            FirebaseUser user = authResult.getUser();
                            String uid = "";
                            if(user != null) uid = user.getUid();
                            String id = Long.toString(response.getId());
                            saveUser(id, uid);
                        })
                        .addOnFailureListener( e -> {
                            LogUtil.e(TAG, "message : " + e.getMessage());
                            finishAffinity();
                        });
            }
        });
    }

    private void saveUser(String id, String uid){
        id = Constants.KAKAO_ID_PREFIX + id;
        String nickName = Util.generateNickname(6);

        /** sharedPreference **/
        SharedPreference.getInstance().putString(Constants.LOGIN_TYPE, Constants.KAKAO);
        SharedPreference.getInstance().putString(Constants.USER_ID, id);
        SharedPreference.getInstance().putString(Constants.USER_NICKNAME, nickName);

        /** firebase **/
        String finalId = id;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("user").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() == 0) {
                    ref.child("user").child(finalId).setValue(new User(finalId, Constants.USER_TYPE_SOCIAL, uid, nickName))
                            .addOnSuccessListener(aVoid -> {
                                startMainActivity();
                            });
                }else{
                    ref.child("user").child(finalId).child("uid").setValue(uid).addOnSuccessListener(aVoid -> {
                        startMainActivity();
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
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