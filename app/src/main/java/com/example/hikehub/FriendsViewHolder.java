package com.example.hikehub;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsViewHolder extends RecyclerView.ViewHolder {
    TextView friendNum;
    ImageButton friendPP;
    Button friendName;
    ImageButton chatIcon;

    public FriendsViewHolder(@NonNull View itemView){
        super(itemView);
        friendNum = itemView.findViewById(R.id.friendNum);
        friendPP = itemView.findViewById(R.id.friendPP);
        friendName = itemView.findViewById(R.id.friendNick);
        chatIcon = itemView.findViewById(R.id.friendChat);
    }
}
