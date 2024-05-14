package com.example.hikehub;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

public class UserScreen extends SuperScreen {
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
        setContentView(R.layout.activity_user_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.UserScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
    }

    public int dpToPx(int dp){
        Resources resources = getResources();

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    public void friendScreen(View v){
        Intent i = new Intent(this, FriendsScreen.class);
        startActivity(i);
    }

    public void casual(View v){
        Intent i = new Intent(this, Casual_Start.class);
        startActivity(i);
    }
    public void challange(View v){
        Intent i = new Intent(this, ChallangeStart.class);
        startActivity(i);
    }
}