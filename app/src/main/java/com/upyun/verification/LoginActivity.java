package com.upyun.verification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.upyun.verification.R;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View l) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, SucceedAvtivity.class));
                LoginActivity.this.finish();
            }
        });
    }
}