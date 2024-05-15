package com.example.hikehub;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
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
        holder.friendName.setText(friends.get(position).getName());
        holder.friendName.setBackground(ResourcesCompat
                .getDrawable(context.getResources() ,R.drawable.chat_icon, context.getTheme()));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
