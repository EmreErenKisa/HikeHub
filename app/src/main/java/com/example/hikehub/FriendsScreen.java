package com.example.hikehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FriendsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView invalidUsername = findViewById(R.id.invalidUsername);
        TextView searchUsername = findViewById(R.id.searchUsername);

        ConstraintLayout.LayoutParams friendsTitleParams =
                (ConstraintLayout.LayoutParams) findViewById(R.id.friendsTitle).getLayoutParams();
        ConstraintLayout.LayoutParams invalidUsernameParams =
                (ConstraintLayout.LayoutParams) invalidUsername.getLayoutParams();
        ConstraintLayout.LayoutParams searchUsernameParams =
                (ConstraintLayout.LayoutParams) searchUsername.getLayoutParams();

        invalidUsernameParams.width = MainActivity.width / 2;
        searchUsernameParams.width = MainActivity.width / 2;

        invalidUsername.setLayoutParams(invalidUsernameParams);
        searchUsername.setLayoutParams(searchUsernameParams);

        findViewById(R.id.addFriendScreen).setVisibility(View.INVISIBLE);
        friendsTitleParams.topMargin = MainActivity.topRectParams.height + 20;
    }

    public void addFriend(View v){
        findViewById(R.id.addFriendScreen).setVisibility(View.VISIBLE);
    }

    public void closeAddFriendScreen(View v){
        findViewById(R.id.addFriendScreen).setVisibility(View.INVISIBLE);
    }

    public void openChat(View v){
        Intent i = new Intent(this, ChatScreen.class);
        startActivity(i);
    }
}