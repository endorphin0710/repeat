package com.behemoth.repeat.mypage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.behemoth.repeat.R;
import com.behemoth.repeat.auth.LoginActivity;
import com.behemoth.repeat.mypage.unregister.UnregisterActivity;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.behemoth.repeat.util.Util;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

public class MyPageActivity extends AppCompatActivity implements MyPageContract.View, View.OnClickListener{

    private MyPageContract.Presenter presenter;

    private TextView tvCurrentVersionCode;
    private TextView tvLatestVersionCode;
    private TextView tvNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        presenter = new MyPagePresenter(this);

        initView();
        setToolbar();

        presenter.getData();
    }

    private void initView(){
        tvCurrentVersionCode = findViewById(R.id.tv_current_versioncode);
        tvLatestVersionCode = findViewById(R.id.tv_latest_versioncode);
        tvNickName = findViewById(R.id.tv_nickname);

        TextView viewLogout = findViewById(R.id.view_logout);
        viewLogout.setOnClickListener(this);

        TextView viewUnregister = findViewById(R.id.view_unregister);
        viewUnregister.setOnClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onRetrieveData(String nickName, String versionName) {
        tvCurrentVersionCode.setText(Util.getVersionName(this));
        tvLatestVersionCode.setText(versionName);
        tvNickName.setText(nickName);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.view_logout){
            AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this,  R.style.dialogTheme);
            confirmBuilder.setMessage(getString(R.string.confirm_logout));
            confirmBuilder.setPositiveButton(getString(R.string.logout), (dialog1, which1) -> {
                String loginType = SharedPreference.getInstance().getString(Constants.LOGIN_TYPE, "");
                if (loginType.equals(Constants.KAKAO)) {
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            logout();
                        }
                    });
                }else{
                    OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance();
                    mOAuthLoginModule.logout(this);
                    logout();
                }
            });
            confirmBuilder.setNegativeButton(getString(R.string.close), (dialog2, which2) -> { });
            confirmBuilder.create().show();
        }else if(id == R.id.view_unregister){
            goToUnregisterActivity();
        }
    }

    private void logout(){
        SharedPreference.getInstance().resetAll();

        Intent i = new Intent(MyPageActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void goToUnregisterActivity(){
        Intent i = new Intent(MyPageActivity.this, UnregisterActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}