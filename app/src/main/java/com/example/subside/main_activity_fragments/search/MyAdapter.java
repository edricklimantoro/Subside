package com.example.subside.main_activity_fragments.search;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<UserProfile> list;
    private String userUnlockedProfiles = "";

    public MyAdapter(List<UserProfile> list, Context context){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        setUserUnlockedProfiles();
        View v = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserProfile user = list.get(position);
        String setMajorFacultyCohort = user.getMajor() +" | " +user.getFaculty() +" "+user.getCohort();

        holder.textName.setText(user.getName());
        holder.textMajor.setText(setMajorFacultyCohort);
        holder.textID.setText(user.getSid());
        holder.textIG.setText(user.getInstagram());
        holder.textEmail.setText(user.getEmail());
        holder.textPhone.setText(user.getPhoneNum());
        holder.textLinkedin.setText(user.getLinkedIn());

        holder.profileItem.setOnClickListener(v -> {
            Intent profileIntent = new Intent(context, ProfileDisplay.class);
            profileIntent.putExtra("profile_uid",user.getUid());
            profileIntent.putExtra("profile_image",user.getProfPictUri());
            profileIntent.putExtra("profile_name",user.getName());
            profileIntent.putExtra("profile_major",setMajorFacultyCohort);
            profileIntent.putExtra("profile_Id",user.getSid());
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        TextView textMajor;
        ImageView imageIcon;
        TextView textID;
        TextView textIG;
        TextView textEmail;
        TextView textPhone;
        TextView textLinkedin;
        CardView profileItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.text_name);
            textMajor = itemView.findViewById(R.id.text_major);
            imageIcon = itemView.findViewById(R.id.imageList);
            textIG=itemView.findViewById(R.id.text_ig);
            textID=itemView.findViewById(R.id.text_id);
            textEmail=itemView.findViewById(R.id.text_email);
            textPhone=itemView.findViewById(R.id.text_phone);
            textLinkedin=itemView.findViewById(R.id.text_linkedin);
            profileItem=itemView.findViewById(R.id.ProfileItem);

        }
    }

    public void setFilter(ArrayList<UserProfile> filterModel){
        list=new ArrayList<>();
        list.addAll(filterModel);
        notifyDataSetChanged();
    };

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
