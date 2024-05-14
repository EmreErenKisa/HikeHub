package com.example.hikehub;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {

    EditText email;
    EditText pass;
    EditText username;
    EditText age;
    EditText weight;
    EditText height;
    RadioButton sexM;
    RadioButton sexF;
    boolean isMale;
    int ageNumber;
    double weightNumber;
    double heightNumber;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acc);

        email = findViewById(R.id.emailTV);
        pass = findViewById(R.id.passtv);
        username = findViewById(R.id.usernTV);
        age = findViewById(R.id.ageTV);
        weight = findViewById(R.id.weightTV);
        height = findViewById(R.id.heightTV);
        sexM = findViewById(R.id.maleRB);
        sexF = findViewById(R.id.femaleRB);
        mAuth = FirebaseAuth.getInstance();

    }

    public void newAccount(View v){
        if(sexF.isChecked()){
            isMale = false;
        }
        else {
            isMale = true;
        }
        ageNumber = Integer.parseInt(age.getText().toString());
        weightNumber = Double.parseDouble(weight.getText().toString());
        heightNumber = Double.parseDouble(height.getText().toString());

        String emailS, passwordS, userS;
        emailS = String.valueOf(email.getText());
        passwordS = String.valueOf(pass.getText());
        userS = String.valueOf(username.getText());

        if(TextUtils.isEmpty(emailS)){
            Toast.makeText(CreateAccount.this,"Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passwordS)){
            Toast.makeText(CreateAccount.this,"Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userS)){
            Toast.makeText(CreateAccount.this,"Enter username", Toast.LENGTH_SHORT).show();
            return;
        }



        mAuth.createUserWithEmailAndPassword(emailS,passwordS)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccount.this, "Acount created.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateAccount.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Account acc = new Account(emailS,passwordS,userS,isMale,ageNumber,heightNumber,weightNumber);
        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }

}