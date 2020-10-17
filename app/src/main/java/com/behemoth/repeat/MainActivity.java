package com.behemoth.repeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.behemoth.repeat.util.LogUtil;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

public class MainActivity extends AppCompatActivity {

    Button logout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_profile);

        textView = findViewById(R.id.textView);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText("hi android!");
                /**Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        OAuthLogin mOAuthLoginModule = OAuthLogin.getInstance();
                        mOAuthLoginModule.showDevelopersLog(true);
                        mOAuthLoginModule.init(
                                MainActivity.this
                                ,getApplicationContext().getString(R.string.naver_client_id)
                                ,getApplicationContext().getString(R.string.naver_client_secret)
                                ,getApplicationContext().getString(R.string.naver_client_name)
                        );
                        boolean logout = mOAuthLoginModule.logoutAndDeleteToken(getApplicationContext());
                        LogUtil.d("juntae", logout? "deleted" : "not deleted");
                    }
                };**/

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Session.getCurrentSession().close();
                    }
                });

                //Thread t = new Thread(r);
                //t.start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
