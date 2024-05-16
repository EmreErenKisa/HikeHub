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

public class CasualFinish extends SuperScreen {
    TextView textView;
    TextView distanceView;
    TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_casual_finish_screen);

        textView = findViewById(R.id.complete);
        timeView = findViewById(R.id.completionTime);
        distanceView = findViewById(R.id.distanceWalkedView);

        textView.setVisibility(View.VISIBLE);
        distanceView.setText( String.valueOf((int)(Casual_Start.distanceToDestination)) );
        timeView.setText( String.valueOf((int)(Casual_Start.time)) );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.CasualFinishScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void mainScreen(View v){
        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }
}