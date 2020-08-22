package com.behemoth.repeat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class MainActivity extends AppCompatActivity {

    Button logout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText("hi android!");
                Runnable r = new Runnable() {
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
                        Log.d("juntae", logout? "deleted" : "not deleted");
                    }
                };

                Thread t = new Thread(r);
                t.start();
            }
        });
    }

}
