package com.example.hikehub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button Signin;
    TextView createAcc;

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

    public void closeFP(View v){
        findViewById(R.id.forgotPassFrame).setVisibility(View.INVISIBLE);
    }

    public void logintoApp(View v){
        mAuth.signInWithEmailAndPassword( String.valueOf(email.getText()), String.valueOf(password.getText()) )
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
                            Toast.makeText(MainActivity.this, "User does not exist.",
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