package com.example.hikehub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileScreen extends SuperScreen {
    ImageButton profilePhoto;
    int SELECT_PICTURE = 200;
    EditText height;
    EditText weight;
    EditText age;
    EditText score;
    TextView gender;
    ImageButton pp;

    Button username;
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profileScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void pickAvatar()
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

    @Override
    public void onStart() {
        super.onStart();

        username.setText((String) UserScreen.acc.get("name"));
        height.setText(String.valueOf(UserScreen.acc.get("height")));
        weight.setText(String.valueOf(UserScreen.acc.get("weight")));
        age.setText(String.valueOf(UserScreen.acc.get("age")));
        score.setText(String.valueOf(UserScreen.acc.get("challengeScore")));

        if (UserScreen.acc.get("profilePhoto") == null) {
            profilePhoto.setForeground(ResourcesCompat.getDrawable(getResources() ,R.drawable.default_pp, getTheme()));
        }
        else {
            profilePhoto.setForeground((Drawable) UserScreen.acc.get("profilePhoto"));
        }

        if ((Boolean) UserScreen.acc.get("male")) {
            gender.setText(R.string.genderMale);
        }
        else{
            gender.setText(R.string.genderFemale);
        }
    }
}