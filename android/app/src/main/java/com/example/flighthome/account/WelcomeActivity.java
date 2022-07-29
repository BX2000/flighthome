package com.example.flighthome.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.flighthome.BasicActivity;
import com.example.flighthome.GlobalVariable;
import com.example.flighthome.R;

public class WelcomeActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setBar();

        GlobalVariable.userid = "0";
        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(settingsIntent);
            }
        });

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(settingsIntent);
            }
        });
    }
}