package com.example.hikehub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button Signin;
    TextView createAcc;

    Button forgotPass;

    TextView FPemail;

    String emailS, passwordS;

    static FirebaseAuth mAuth;

    static FirebaseFirestore db;
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

        emailS = String.valueOf(email.getText());
        passwordS = String.valueOf(password.getText());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
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
        emailS = String.valueOf(email.getText());
        passwordS = String.valueOf(password.getText());

        if(TextUtils.isEmpty(emailS)){
            Toast.makeText(MainActivity.this,"Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passwordS)){
            Toast.makeText(MainActivity.this,"Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("MainActivity", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            login();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("MainActivity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Wrong email or password.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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