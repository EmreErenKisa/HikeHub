package com.example.hikehub;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class UserScreen extends SuperScreen {
    protected static String fireStoreID;

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

    private static Map<String, Object> data;
    public static Map<String, Object> getUserDataWithEmail(String email){
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("email",
                        email).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("UserScreen", document.getId() + " => " + document.getData());
                                    data = document.getData();
                                }
                            } else {
                                Log.d("UserScreen", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        return data;
    }
}