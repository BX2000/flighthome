package com.example.flighthome.account;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flighthome.BasicActivity;
import com.example.flighthome.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AccountActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        setBar();

        findViewById(R.id.button_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(AccountActivity.this, SaveActivity.class);
                startActivity(settingsIntent);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient sign_in = GoogleSignIn.getClient(AccountActivity.this, gso);
                if(GoogleSignIn.getLastSignedInAccount(AccountActivity.this) != null){
                    signOut(sign_in);
                    Intent settingsIntent = new Intent(AccountActivity.this, WelcomeActivity.class);
                    startActivity(settingsIntent);
                }
            }
        });

        TextView name = findViewById(R.id.name_text);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(AccountActivity.this);
        if( account != null){
            name.setText(account.getGivenName());
        }

    }
    private void signOut(GoogleSignInClient mGoogleSignInClient) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}