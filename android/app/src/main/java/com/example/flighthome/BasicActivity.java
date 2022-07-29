package com.example.flighthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.flighthome.account.AccountActivity;
import com.example.flighthome.account.WelcomeActivity;
import com.example.flighthome.home.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

public abstract class BasicActivity extends AppCompatActivity {

    public void setBar()
    {
        ImageView account_image = findViewById(R.id.account_image);
        account_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( GoogleSignIn.getLastSignedInAccount(getApplicationContext())!=null){
                    Intent settingsIntent = new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(settingsIntent);
                }else {
                    Intent settingsIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(settingsIntent);
                }
            }
        });

        ImageView home_image = findViewById(R.id.home_image);
        home_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(settingsIntent);
            }
        });

    }
}