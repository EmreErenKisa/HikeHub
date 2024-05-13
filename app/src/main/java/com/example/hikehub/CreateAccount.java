package com.example.hikehub;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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


        Account acc = new Account(email.getText().toString(),pass.getText().toString(),username.getText().toString(),isMale,ageNumber,heightNumber,weightNumber);
        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }

}