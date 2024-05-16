package com.example.hikehub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ProfileScreen extends SuperScreen {
    ImageButton profilePhoto;
    int SELECT_PICTURE = 200;
    EditText height;
    EditText weight;
    EditText age;
    TextView score;
    TextView gender;
    ImageButton pp;
    TextView username;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        profilePhoto = findViewById(R.id.profilePhoto);
        setContentView(R.layout.profile_screen);

        height = findViewById(R.id.heightEdit);
        weight = findViewById(R.id.weightEdit);
        age = findViewById(R.id.ageEdit);
        gender = findViewById(R.id.sex);
        score = findViewById(R.id.score);
        username = findViewById(R.id.username);

        ConstraintLayout.LayoutParams ppParams = (ConstraintLayout.LayoutParams) profilePhoto.getLayoutParams();
        ppParams.topMargin = SuperScreen.topRectParams.height;

        profilePhoto.setLayoutParams(ppParams);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profileScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void pickAvatar(View v)
    {
        imageChooser();
    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    profilePhoto.setImageURI(selectedImageUri);
                }
            }
        }
    }
    public void setHeight(View v)
    {
        Map<String, Object> acc = SuperScreen.data;
        EditText text = findViewById(R.id.heightEdit);
        acc.replace("height", String.valueOf(text.getText().toString()));
        db.collection("users").document(SuperScreen.fireStoreID).update(acc);
    }
    public void setWeight(View v)
    {
        Map<String, Object> acc = SuperScreen.data;
        EditText text = findViewById(R.id.weightEdit);
        String input = text.getText().toString();
        acc.replace("weight", String.valueOf(text.getText().toString()));
        db.collection("users").document(SuperScreen.fireStoreID).update(acc);
    }
    public void setAge(View v)
    {
        Map<String, Object> acc = SuperScreen.data;
        EditText text = findViewById(R.id.ageEdit);
        acc.replace("age", String.valueOf(text.getText().toString()));
        db.collection("users").document(SuperScreen.fireStoreID).update(acc);
    }
    public void deleteAccount(View v)
    {
        findViewById(R.id.confirmDeleteScreen).setVisibility(View.VISIBLE);
    }
    public void closeDeleteAccount(View v)
    {
        findViewById(R.id.confirmDeleteScreen).setVisibility(View.INVISIBLE);
    }

    public void logout(View V){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void confirmDeleteAccount(View v){
        mAuth.getCurrentUser().delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("ProfileScreen", "User account deleted.");
                        }
                    }
                });

        db.collection("users").document(SuperScreen.fireStoreID).delete();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onStart() {
        super.onStart();

        Map<String, Object> acc = SuperScreen.data;

        if (acc == null) {
            Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
        }
        username.setText(String.valueOf(acc.get("name")));
        height.setText(String.valueOf(acc.get("height")));
        weight.setText(String.valueOf(acc.get("weight")));
        age.setText(String.valueOf(acc.get("age")));
        score.setText(String.valueOf(acc.get("challengeScore")));

        if (acc.get("profilePhoto") == null) {
            profilePhoto.setForeground(ResourcesCompat.getDrawable(getResources() ,R.drawable.default_pp, getTheme()));
        }
        else {
            profilePhoto.setForeground((Drawable) acc.get("profilePhoto"));
        }

        if ((Boolean) acc.get("male")) {
            gender.setText(R.string.genderMale);
        }
        else{
            gender.setText(R.string.genderFemale);
        }
    }
}