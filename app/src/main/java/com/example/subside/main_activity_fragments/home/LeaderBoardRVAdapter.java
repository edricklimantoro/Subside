package com.example.subside.main_activity_fragments.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.subside.ProfileDisplay;
import com.example.subside.R;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.db.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardRVAdapter extends RecyclerView.Adapter<LeaderBoardRVAdapter.LeaderBoardViewHolder>{

    Context context;
    List<UserProfile> list;
    private String userUnlockedProfiles = "";

    public LeaderBoardRVAdapter(List<UserProfile> list, Context context){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public LeaderBoardRVAdapter.LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        setUserUnlockedProfiles();
        View v = LayoutInflater.from(context).inflate(R.layout.leaderboard_item,parent,false);
        return new LeaderBoardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        UserProfile user = list.get(position);
        String setMajorFacultyCohort = user.getMajor() +" | " +user.getFaculty() +" "+user.getCohort();


        Uri imageUri = Uri.parse(user.getProfPictUri());
        Glide.with(context).load(user.getProfPictUri()).placeholder(R.drawable.black_profile_picture).dontAnimate().into(holder.profImage);
        holder.textRank.setText(Integer.toString(position+1));
        holder.textName.setText(user.getName());
        holder.textCount.setText(Integer.toString(user.getUnlockedProfilesCount()));

        holder.leaderboardItem.setOnClickListener(v -> {
            Intent profileIntent = new Intent(context, ProfileDisplay.class);
            profileIntent.putExtra("profile_uid",user.getUid());
            profileIntent.putExtra("profile_image",user.getProfPictUri());
            profileIntent.putExtra("profile_name",user.getName());
            profileIntent.putExtra("profile_major",setMajorFacultyCohort);
            profileIntent.putExtra("profile_Id",(!user.isHideSID() ? user.getSid() : "**********"));
            profileIntent.putExtra("profile_Ig",user.getInstagram());
            profileIntent.putExtra("profile_email",user.getEmail());
            profileIntent.putExtra("profile_phone",user.getPhoneNum());
            profileIntent.putExtra("profile_linkedin",user.getLinkedIn());
            profileIntent.putExtra("profile_funFact",user.getFunFact());
            profileIntent.putExtra("user_unlocked_profiles",userUnlockedProfiles);
            context.startActivity(profileIntent);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LeaderBoardViewHolder extends RecyclerView.ViewHolder {
        TextView textRank;
        CircleImageView profImage;
        TextView textName;
        TextView textCount;
        CardView leaderboardItem;

        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            textRank = itemView.findViewById(R.id.text_rank);
            profImage = itemView.findViewById(R.id.leaderbrd_profImg);
            textName = itemView.findViewById(R.id.leaderbrd_name);
            textCount = itemView.findViewById(R.id.leaderbrd_count);
            leaderboardItem = itemView.findViewById(R.id.LeaderBoardItem);

        }
    }

    private void setUserUnlockedProfiles() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseHelper.getOne(mAuth.getCurrentUser().getUid()).child("unlockedProfiles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userUnlockedProfiles = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
