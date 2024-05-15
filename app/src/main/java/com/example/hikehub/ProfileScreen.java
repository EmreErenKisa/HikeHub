package com.example.hikehub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileScreen extends SuperScreen {
    ImageButton myImageButton;

    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        myImageButton = findViewById(R.id.profilePhoto);
        setContentView(R.layout.profile_screen);
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
                    myImageButton.setImageURI(selectedImageUri);
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


}