package com.example.hikehub;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FriendsScreen extends SuperScreen {

    ConstraintLayout friendsView;
    RecyclerView friendsList;
    RecyclerView friendRequestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FriendsScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView searchUsername = findViewById(R.id.searchUsername);

        LinearLayout.LayoutParams linearLayoutParams =
                (LinearLayout.LayoutParams) findViewById(R.id.linearLayout).getLayoutParams();
        ConstraintLayout.LayoutParams searchUsernameParams =
                (ConstraintLayout.LayoutParams) searchUsername.getLayoutParams();

        linearLayoutParams.topMargin = SuperScreen.topRectParams.height;
        findViewById(R.id.linearLayout).setLayoutParams(linearLayoutParams);

        searchUsernameParams.width = UserScreen.width / 2;
        searchUsername.setLayoutParams(searchUsernameParams);

        findViewById(R.id.addFriendScreen).setVisibility(View.INVISIBLE);


        friendsView = findViewById(R.id.friendView);
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

    public void searchFriend(View v){
        String username = ((EditText) findViewById(R.id.searchFriendBox)).getText().toString();

        Account account = Account.castFromDB(UserScreen.getUserDataWithEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        if (getUserWithName(username).getFriendList().contains(account)){
            Toast.makeText(this, "You are already friends.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getUserWithName(username).getFriendRequests().contains(account)){
            Toast.makeText(this, "You have already sent a request.", Toast.LENGTH_SHORT).show();
            return;
        }

        getUserWithName(username).getFriendRequests().add(account);
    }

    @Override
    public void onStart(){
        super.onStart();

        friendsList = findViewById(R.id.friendsList);
        friendRequestsList = findViewById(R.id.friendRequestsList);
        updateFriendViews();
    }

    private void updateFriendViews(){
        Account account = Account.castFromDB(UserScreen.getUserDataWithEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        ArrayList<Account> friends = account.getFriendList();
        ArrayList<Account> friendRequests = account.getFriendRequests();

        if (friendRequests == null || friendRequests.size() == 0){
                findViewById(R.id.friendRequests).setVisibility(View.GONE);
                friendRequestsList.setVisibility(View.GONE);
        }

        Account temp;

        for (int i = 0; i < friends.size(); i++) {
            for (int j = 1; j < friends.size(); j++) {
                if (friends.get(j).getChallengeScore() < friends.get(j-1).getChallengeScore()) {
                    temp = friends.get(j);
                    friends.set(j,friends.get(j-1));
                    friends.set(j-1, temp);
                }
            }
        }

        friendsList.setLayoutManager(new LinearLayoutManager(this));
        friendsList.setAdapter(new FriendsAdapter(getApplicationContext(), friends));
        friendRequestsList.setLayoutManager(new LinearLayoutManager(this));
        friendRequestsList.setAdapter(new FriendsAdapter(getApplicationContext(), friendRequests));
    }

    private Account friend;
    private Account getUserWithName(String name){
        MainActivity.db.collection("users").whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("UserScreen", document.getId() + " => " + document.getData());
                                friend = Account.castFromDB(document.getData());
                            }
                        } else {
                            Log.d("UserScreen", "Error getting documents: ", task.getException());
                        }
                    }
                });
        if (friend == null) {
            return new Account();
        }
        return friend;
    }
}