package com.behemoth.repeat.mypage.unregister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.behemoth.repeat.R;
import com.behemoth.repeat.auth.LoginActivity;

public class GoodByeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_bye);

        Button btnGoodBye = findViewById(R.id.btnGoodBye);
        btnGoodBye.setOnClickListener(v -> {
            goToLoginActivity();
        });
    }

    private void goToLoginActivity(){
        Intent i = new Intent(GoodByeActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        goToLoginActivity();
    }
}