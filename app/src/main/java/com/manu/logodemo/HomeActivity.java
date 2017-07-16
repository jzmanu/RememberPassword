package com.manu.logodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity{
    private TextView tv_exitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_exitLogin = (TextView) findViewById(R.id.tv_exitLogin);
        tv_exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
