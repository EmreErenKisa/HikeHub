package com.example.hikehub;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsViewHolder> {

    Context context;
    List<Account> friends;

    public FriendsAdapter(Context context, List<Account> friends){
        this.friends = friends;
        this.context = context;
    }
    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.activity_friends_screen, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        holder.friendNum.setText(position + 1);
        holder.friendPP.setBackground(friends.get(position).getProfilePhoto());
        holder.friendPP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                friends.get(position + 1);

                Intent i = new Intent(context,FriendProfile.class);
                context.startActivity(i);
            }
        });

        holder.friendName.setText(friends.get(position).getName());
        holder.friendName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                friends.get(position + 1);

                Intent i = new Intent(context,FriendProfile.class);
                context.startActivity(i);
            }
        });

        holder.chatIcon.setBackground(ResourcesCompat
                .getDrawable(context.getResources() ,R.drawable.chat_icon, context.getTheme()));
        holder.chatIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                friends.get(position + 1);

                Intent i = new Intent(context,ChatScreen.class);
                context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return friends.size();
    }
}
