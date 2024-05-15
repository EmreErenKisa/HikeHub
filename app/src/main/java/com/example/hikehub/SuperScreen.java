package com.example.hikehub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

import java.util.Map;

public class SuperScreen extends AppCompatActivity {

    public static ImageView hikehubLogo;
    public static ImageButton profilePhoto;
    public static TextView topRectangle;
    public static ConstraintLayout.LayoutParams hhLogoParams;
    public static ConstraintLayout.LayoutParams profilePhotoParams;
    public static ConstraintLayout.LayoutParams topRectParams;

    public static int height;
    public static int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.super_screen);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        hikehubLogo = findViewById(R.id.hikehubLogo);
        profilePhoto = findViewById(R.id.profilePhoto);
        topRectangle = findViewById(R.id.topRectangle);

        hhLogoParams = (ConstraintLayout.LayoutParams) hikehubLogo.getLayoutParams();
        profilePhotoParams = (ConstraintLayout.LayoutParams) profilePhoto.getLayoutParams();
        topRectParams = (ConstraintLayout.LayoutParams) topRectangle.getLayoutParams();

        hhLogoParams.width = width * 7 / 12;
        hikehubLogo.setLayoutParams(hhLogoParams);

        hikehubLogo.post(new Runnable() {
            @Override
            public void run() {
                int hikehubLogoHeight = hikehubLogo.getHeight();
                profilePhotoParams.height = hikehubLogoHeight;
                profilePhotoParams.width = hikehubLogoHeight;

                topRectParams.height = hikehubLogoHeight * 130 / 100;
                int margin = hikehubLogoHeight * 15 / 100;
                hhLogoParams.topMargin = margin;
                profilePhotoParams.topMargin = margin;
                profilePhotoParams.rightMargin = margin;

                hhLogoParams.leftMargin = (width - (hhLogoParams.width +
                        profilePhotoParams.width + margin)) / 2;

                profilePhoto.setLayoutParams(profilePhotoParams);
                topRectangle.setLayoutParams(topRectParams);
                hikehubLogo.setLayoutParams(hhLogoParams);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SuperScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String, Object> acc = UserScreen.getUserDataWithEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        if (acc == null) return;
        if (acc.get("profilePhoto") == null) return;

        ImageButton pp = (ImageButton) findViewById(R.id.profilePhoto);
        pp.setForeground((Drawable) acc.get("profilePhoto"));
    }

    public void profileScreen(View v){
        Intent i = new Intent(this, ProfileScreen.class);
        startActivity(i);
    }

    public void backToMain(View v){
        Intent i = new Intent(this, UserScreen.class);
        startActivity(i);
    }
}