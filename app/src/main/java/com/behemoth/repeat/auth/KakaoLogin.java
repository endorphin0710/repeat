package com.behemoth.repeat.auth;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

    private static final String TAG = "KakaoLoginTAG";
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
            startLoginActivity(1);
        }
    }

    private void requestMe() {
        LogUtil.d(TAG, "requestMe: ");
        List<String> keys = new ArrayList<>();

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                LogUtil.d(TAG, "failed to get user info. msg=" + errorResult);
                startLoginActivity(2);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                startLoginActivity(3);
            }

            @Override
            public void onSuccess(MeV2Response response) {
                String token = Session.getCurrentSession().getTokenInfo().getAccessToken();
                LogUtil.d(TAG, "token -> " + token);

                LogUtil.d(TAG, "reuqestMe -> onSuccess ");

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInAnonymously()
                        .addOnSuccessListener(authResult -> {
                            LogUtil.d(TAG, "Firebase Signin Success ");
                            FirebaseUser user = authResult.getUser();
                            String uid = "";
                            if(user != null) {
                                uid = user.getUid();
                                LogUtil.d(TAG, "UID : " + uid);
                            }
                            String id = Long.toString(response.getId());
                            saveUser(id, uid);
                        })
                        .addOnFailureListener( e -> {
                            LogUtil.e(TAG, "message : " + e.getMessage());
                            startLoginActivity(4);
                        });
            }
        });
    }

    private void saveUser(String id, String uid){
        LogUtil.d(TAG, "Save User Data");
        id = Constants.KAKAO_ID_PREFIX + id;
        String nickName = Util.generateNickname(6);
        LogUtil.d(TAG, "Nickname : " + nickName);

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
                if(dataSnapshot.getChildrenCount() <= 0) {
                    LogUtil.d(TAG, "UID not found");
                    ref.child("user").child(finalId).setValue(new User(finalId, Constants.USER_TYPE_SOCIAL, uid, nickName))
                            .addOnSuccessListener(aVoid -> {
                                startMainActivity();
                            });
                }else{
                    LogUtil.d(TAG, "UID exists : " + uid);
                    ref.child("user").child(finalId).child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String uidStored = (String) dataSnapshot.getValue();
                            LogUtil.d(TAG, "UID stored : " + uidStored);
                            if(uid.equals(uidStored)){
                                startMainActivity();
                            }else{
                                ref.child("user").child(finalId).child("uid").setValue(uid)
                                        .addOnSuccessListener(aVoid -> {
                                            startMainActivity();
                                        })
                                        .addOnFailureListener(aVoid ->{
                                            startLoginActivity(5);
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            startLoginActivity(6);
                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                startLoginActivity(7);
            }
        });
    }

    private void startLoginActivity(int a){
        Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Kakao login error with index : " + a);

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