package com.example.hikehub;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CreateAccount extends AppCompatActivity {

    EditText email;
    EditText pass;
    EditText confpass;
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
        confpass = findViewById(R.id.confpasstv);
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

        String emailS, passwordS, confPassS, userS;
        emailS = String.valueOf(email.getText());
        passwordS = String.valueOf(pass.getText());
        confPassS = String.valueOf(confpass.getText());
        userS = String.valueOf(username.getText());

        if(TextUtils.isEmpty(emailS)){
            Toast.makeText(CreateAccount.this,"Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwordS.length() < 6){
            Toast.makeText(CreateAccount.this,"Enter password with at least 6 characters",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userS)){
            Toast.makeText(CreateAccount.this,"Enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!confPassS.equals(passwordS)) {
            Toast.makeText(CreateAccount.this,"Passwords are not the same. Failed!", Toast.LENGTH_SHORT).show();
            return;
        }

        Account acc = new Account(emailS,passwordS,userS,isMale,ageNumber,heightNumber,weightNumber);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Checks if user already exists
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getData().equals(acc)) {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(CreateAccount.this, "User already exists.",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        } else {
                            Log.w("CreateAccount", "Error getting documents.", task.getException());
                        }
                    }
                });

        mAuth.createUserWithEmailAndPassword(emailS,passwordS)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccount.this, "Acount created.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateAccount.this, "Please enter a valid email.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

        // Creates new user if one already DNE
        db.collection("users")
                .add(acc)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("CreateAccount", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("CreateAccount", "Error adding document", e);
                    }
                });

        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }

}