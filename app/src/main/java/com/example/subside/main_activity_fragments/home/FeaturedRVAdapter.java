package com.example.subside.main_activity_fragments.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subside.ProfileDisplay;
import com.example.subside.R;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.db.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FeaturedRVAdapter extends RecyclerView.Adapter<FeaturedRVAdapter.FeaturedViewHolder>{

    Context context;
    List<UserProfile> list;
    private String userUnlockedProfiles = "";

    public FeaturedRVAdapter(List<UserProfile> list, Context context){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public FeaturedRVAdapter.FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        setUserUnlockedProfiles();
        View v = LayoutInflater.from(context).inflate(R.layout.featured_item,parent,false);
        return new FeaturedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        UserProfile user = list.get(position);
        String setMajorFacultyCohort = user.getMajor() +" | " +user.getFaculty() +" "+user.getCohort();

        holder.textName.setText(user.getName());
        holder.textMajor.setText(user.getMajor());

        holder.featuredItem.setOnClickListener(v -> {
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

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView textName;
        TextView textMajor;
        CardView featuredItem;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.featured_img);
            textName = itemView.findViewById(R.id.featured_name);
            textMajor = itemView.findViewById(R.id.featured_major);
            featuredItem = itemView.findViewById(R.id.FeaturedItem);

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
