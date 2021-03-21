package com.behemoth.repeat.mypage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.behemoth.repeat.R;
import com.behemoth.repeat.auth.LoginActivity;
import com.behemoth.repeat.mypage.faq.FaqActivity;
import com.behemoth.repeat.mypage.nickname.NicknameActivity;
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

    private LottieAnimationView progressBar;
    private ConstraintLayout loadingLayout;

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

        loadingLayout = findViewById(R.id.loading_layout_mypage);
        loadingLayout.bringToFront();

        progressBar = findViewById(R.id.mypage_progressbar);
        progressBar.setRepeatCount(LottieDrawable.INFINITE);
        progressBar.setRepeatMode(LottieDrawable.RESTART);

        TextView viewNickname = findViewById(R.id.view_nickname);
        viewNickname.setOnClickListener(this);

        TextView viewFaq = findViewById(R.id.view_faq);
        viewFaq.setOnClickListener(this);

        TextView viewInit = findViewById(R.id.view_init);
        viewInit.setOnClickListener(this);

        TextView viewLogout = findViewById(R.id.view_logout);
        viewLogout.setOnClickListener(this);

        TextView viewUnregister = findViewById(R.id.view_unregister);
        viewUnregister.setOnClickListener(this);

        TextView viewVersion = findViewById(R.id.view_version);
        viewVersion.setOnClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
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
    public void showProgressBar(){
        getSupportActionBar().hide();
        loadingLayout.setVisibility(View.VISIBLE);
        progressBar.playAnimation();
    }

    @Override
    public void hideProgressBar(){
        getSupportActionBar().show();
        loadingLayout.setVisibility(View.GONE);
        progressBar.pauseAnimation();
    }

    @Override
    public void onDeleteSuccess() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDeleteFailure() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.view_logout){
            showLogoutDialog();
        }else if(id == R.id.view_init){
            showInitDialog();
        }else if(id == R.id.view_faq){
            goToFaqActivity();
        }else if(id == R.id.view_unregister){
            goToUnregisterActivity();
        }else if(id == R.id.view_nickname){
            goToNickNameActivity();
        }else if(id == R.id.view_version){
            checkVersion();
        }
    }

    private void showLogoutDialog(){
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
        AlertDialog dialog = confirmBuilder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#B00020"));
    }

    private void showInitDialog(){
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this,  R.style.dialogTheme);
        confirmBuilder.setMessage(getString(R.string.init_desc));
        confirmBuilder.setPositiveButton(getString(R.string.delete), (dialog1, which1) -> {
            presenter.initialize();
        });
        confirmBuilder.setNegativeButton(getString(R.string.close), (dialog2, which2) -> { });
        AlertDialog dialog = confirmBuilder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#B00020"));
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

    private void goToNickNameActivity() {
        Intent i = new Intent(MyPageActivity.this, NicknameActivity.class);
        startActivityForResult(i, Constants.REQUEST_NICKNAME_CHANGE, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void goToFaqActivity(){
        Intent i = new Intent(MyPageActivity.this, FaqActivity.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void checkVersion(){
        String currentVersion = (String) tvCurrentVersionCode.getText();
        String lastesttVersion = (String)tvLatestVersionCode.getText();
        if(currentVersion.equals(lastesttVersion)){
            Util.createAlertDialog(this, getString(R.string.no_need_to_update), getString(R.string.close));
        }else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_NICKNAME_CHANGE){
            if(resultCode == RESULT_OK) presenter.getData();
        }
    }

}