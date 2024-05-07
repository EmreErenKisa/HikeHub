package com.example.hikehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button Signin;
    TextView createAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailTV);
        password = findViewById(R.id.passtv);
        Signin = findViewById(R.id.signinButton);
        createAcc = findViewById(R.id.createNewAccount);
    }

    public void createAccount(View v){
        Intent i = new Intent(this, CreateAccount.class);
        startActivity(i);
    }

    public void forgotPass(View v){
        findViewById(R.id.forgotPassFrame).setVisibility(View.VISIBLE);
    }

    public void closeFP(View v){
        findViewById(R.id.forgotPassFrame).setVisibility(View.INVISIBLE);
    }

    public void logintoApp(View v){
        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }
}