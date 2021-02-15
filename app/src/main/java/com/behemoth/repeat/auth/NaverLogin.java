package com.behemoth.repeat.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.behemoth.repeat.main.MainActivity;
import com.behemoth.repeat.R;
import com.behemoth.repeat.model.User;
import com.behemoth.repeat.retrofit.RetrofitService;
import com.behemoth.repeat.retrofit.RetrofitUtil;
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
import com.google.gson.JsonObject;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NaverLogin extends AppCompatActivity {

    private static final String TAG = "NaverLogin";
    private Context mContext;

    private static OAuthLogin mOAuthLoginModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = getApplicationContext();

        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();
                initNaver();
                Looper.loop();
            }
        };
        thread.start();
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

            String apiURL = "https://openapi.naver.com/";
            Retrofit retrofit = RetrofitUtil.getRetrofitInstance(apiURL);
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            Call<JsonObject> call = retrofitService.me("Bearer " + token);
            call.enqueue(new retrofit2.Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.body() == null) return;

                    String resultCode = response.body().get("resultcode").getAsString();
                    if(!resultCode.equals("00")) return;

                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInAnonymously().addOnSuccessListener(authResult -> {
                        String uid = authResult.getUser() == null ? "" : authResult.getUser().getUid();
                        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(userId).child("uid");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String uidStored = (String) dataSnapshot.getValue();
                                Log.d(TAG, "UID : " + dataSnapshot.getValue());
                                if(uidStored.equals(uid)){
                                    Log.d(TAG, "UID identified");
                                }else{
                                    Log.d(TAG, "UID unidentified / reset UID");
                                    ref.setValue(uid);
                                }
                                Intent i = new Intent(mContext, MainActivity.class);
                                startActivity(i);
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                        });
                    });

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d(TAG, "FAILURE : " + t.getMessage());
                }
            });

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

                String apiURL = "https://openapi.naver.com/";
                Retrofit retrofit = RetrofitUtil.getRetrofitInstance(apiURL);
                RetrofitService retrofitService = retrofit.create(RetrofitService.class);

                Call<JsonObject> call = retrofitService.me("Bearer " + token);
                call.enqueue(new retrofit2.Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        String resultCode = response.body().get("resultcode").getAsString();
                        if(!resultCode.equals("00")) return;

                        JsonObject obj = (JsonObject) response.body().get("response");

                        String token = mOAuthLoginModule.getAccessToken(ctx);
                        LogUtil.d(TAG, "naverToken: " + token);

                        String id = Constants.NAVER_ID_PREFIX + obj.get("id").getAsString();
                        LogUtil.d(TAG, "naverId: " + id);

                        String nickName = Util.generateNickname(6);

                        // SharedPreference
                        SharedPreference.getInstance().putString(Constants.LOGIN_TYPE, Constants.NAVER);
                        SharedPreference.getInstance().putString(Constants.USER_ID, id);
                        SharedPreference.getInstance().putString(Constants.USER_NICKNAME, nickName);

                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signInAnonymously()
                                .addOnSuccessListener(authResult -> {
                                    FirebaseUser user = authResult.getUser();
                                    String uid = "";
                                    if(user != null) uid = user.getUid();
                                    // Firebase
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    ref.child("user").child(id).setValue(new User(id, Constants.USER_TYPE_SOCIAL, uid, nickName))
                                            .addOnSuccessListener(aVoid -> {
                                                Intent i = new Intent(parent, MainActivity.class);
                                                parent.startActivity(i);
                                                parent.finish();
                                            });
                                })
                                .addOnFailureListener( e -> {
                                    LogUtil.e(TAG, "message : " + e.getMessage());
                                    parent.finishAffinity();
                                });

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d(TAG, "FAILURE : " + t.getMessage());
                    }
                });
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(ctx).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(ctx);
                LogUtil.d(TAG, "errorCode:" + errorCode + ", errorDesc:" + errorDesc);

                Intent i = new Intent(parent, LoginActivity.class);
                parent.startActivity(i);
                parent.finish();
            }
            this.getLooper().quit();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
