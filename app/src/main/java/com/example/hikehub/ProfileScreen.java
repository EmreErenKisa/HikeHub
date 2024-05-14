package com.example.hikehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileScreen extends SuperScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profileScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void setHeight(View v)
    {
        EditText text = findViewById(R.id.heightEdit);
        String input = text.getText().toString();
    }
    public void setWeight(View v)
    {
        EditText text = findViewById(R.id.weightEdit);
        String input = text.getText().toString();
    }
    public void setAge(View v)
    {
        EditText text = findViewById(R.id.ageEdit);
        String input = text.getText().toString();
    }
    public void deleteAccount(View v)
    {
        findViewById(R.id.confirmDeleteScreen).setVisibility(View.VISIBLE);
    }
    public void closeDeleteAccount(View v)
    {
        findViewById(R.id.confirmDeleteScreen).setVisibility(View.INVISIBLE);
    }
    public void goBackToMain(View v){
        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }


}