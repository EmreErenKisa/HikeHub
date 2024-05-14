package com.example.hikehub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button Signin;
    TextView createAcc;

    Button forgotPass;

    TextView FPemail;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailTV);
        password = findViewById(R.id.passtv);
        Signin = findViewById(R.id.signinButton);
        createAcc = findViewById(R.id.createNewAccount);
        forgotPass = findViewById(R.id.forgotPassB);
        FPemail = findViewById(R.id.emailTV2);

        // Initialize Firebase Auth,,
        mAuth = FirebaseAuth.getInstance();

        String emailS, passwordS, userS;
        emailS = String.valueOf(email.getText());
        passwordS = String.valueOf(password.getText());

    }

    public void createAccount(View v){
        Intent i = new Intent(this, CreateAccount.class);
        startActivity(i);
    }

    public void forgotPass(View v){
        findViewById(R.id.forgotPassFrame).setVisibility(View.VISIBLE);
    }

    public void forgotPassButton(View v){
        String StrEmail = FPemail.getText().toString().trim();
        if(!TextUtils.isEmpty(StrEmail)){
            ResetPassword();
        }
        else {
            FPemail.setError("Email cannot be empty!!!");
        }
    }

    private void ResetPassword(){


        mAuth.sendPasswordResetEmail(FPemail.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this,"Reset Password link has been sent to your registered email", Toast.LENGTH_SHORT).show();
                        findViewById(R.id.forgotPassFrame).setVisibility(View.INVISIBLE);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void closeFP(View v){
        findViewById(R.id.forgotPassFrame).setVisibility(View.INVISIBLE);
    }

    public void logintoApp(View v){
        // TODO: Check if the user data available in firebase

        login();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null) {
            login();
        }
    }

    private void login(){
        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }
}