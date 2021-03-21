package com.behemoth.repeat.mypage.unregister;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.behemoth.repeat.R;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

public class UnregisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unregister);

        initView();
        setToolbar();
    }

    private void initView(){
        Button btnUnregister = findViewById(R.id.btnUnregister);
        btnUnregister.setOnClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onClick(View v) {
        int id  = v.getId();
        if(id == R.id.btnUnregister){
            unregister();
        }
    }

    private void unregister(){
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");

        DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child("book").child(userId);

        bookRef.removeValue().addOnSuccessListener(bv -> {
            DatabaseReference recentsRef = FirebaseDatabase.getInstance().getReference().child("user").child(userId);
            recentsRef.removeValue().addOnSuccessListener(rv -> {
                String loginType = SharedPreference.getInstance().getString(Constants.LOGIN_TYPE, "");
                if (loginType.equals(Constants.KAKAO)) {
                    UserManagement.getInstance()
                            .requestUnlink(new UnLinkResponseCallback() {
                                @Override
                                public void onSessionClosed(ErrorResult errorResult) {
                                }

                                @Override
                                public void onFailure(ErrorResult errorResult) {
                                }
                                @Override
                                public void onSuccess(Long result) {
                                    goToGoodByeActivity();
                                    SharedPreference.getInstance().resetAll();
                                }
                            });
                }else{
                    OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance();
                    mOAuthLoginModule.logoutAndDeleteToken(this);
                    goToGoodByeActivity();
                    SharedPreference.getInstance().resetAll();
                }
            });
        });

    }

    private void goToGoodByeActivity(){
        Intent i = new Intent(UnregisterActivity.this, GoodByeActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}