package com.example.hikehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class CasualFail extends SuperScreen {
    TextView textView;
    TextView distanceView;
    TextView timeView;
    TextView calorieView;
    TextView speedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_casual_finish_screen);

        textView = findViewById(R.id.fail);
        timeView = findViewById(R.id.completionTime);
        distanceView = findViewById(R.id.distanceWalkedView);
        calorieView = findViewById(R.id.caloriesView);
        speedView = findViewById(R.id.speedResult);

        Account account = UserScreen.user;

        textView.setVisibility(View.VISIBLE);
        distanceView.setText( String.valueOf((int)(Casual_Start.distanceToDestination) + "m") );
        timeView.setText( String.valueOf((int)(Casual_Start.time)/1000) + " seconds");
        calorieView.setText( String.valueOf(caloriesBurned(account)) + " calories");
        speedView.setText( String.valueOf(calculateSpeed()) + "m/s");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.CasualFinishScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public double calculateSpeed(){
        return Casual_Start.distanceToDestination / (Casual_Start.time/1000);
    }

    public double caloriesBurned(Account a){
        final double calculationConstant1 = 0.035;
        final double calculationConstant2 = 0.029;
        double calorie = (calculationConstant1* a.getWeight()) + (calculateSpeed() / a.getHeight())*calculationConstant2*a.getWeight();
        return calorie;
    }

    public void mainScreen(View v){
        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }
}