package com.example.subside.main_activity_fragments.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.subside.EditProfile;
import com.example.subside.ProfileDisplay;
import com.example.subside.R;
import com.example.subside.auth.SignIn;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.db.UserProfile;
import com.example.subside.internetConnection;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AccountFragment extends Fragment {

    private UserProfile currentUserProfile;
    private FirebaseAuth mAuth;
    private ImageView profImg;
    private TextView name, major, faculty, cohort, sid, instagram, email, phoneNum, linkedIn, funFact, unlockedCount;
    private Switch hideSID, disableFeatured, hideAccount;
    private TextView btnEditProfile, btnLogout;
    private ShimmerFrameLayout accountShimmer;
    private ScrollView accountShow;
    internetConnection connection;
    boolean needshimmer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        if(savedInstanceState!=null){
            needshimmer=savedInstanceState.getBoolean("needshimmer",false);
        }
        else{
            needshimmer=true;
        }

        connection = new internetConnection(this.getContext());

        profImg = view.findViewById(R.id.acc_profile_img);
        name = view.findViewById(R.id.acc_name);
        major = view.findViewById(R.id.acc_major);
        faculty = view.findViewById(R.id.acc_faculty);
        cohort = view.findViewById(R.id.acc_cohort);
        sid = view.findViewById(R.id.acc_sid);
        instagram = view.findViewById(R.id.acc_instagram);
        email = view.findViewById(R.id.acc_email);
        phoneNum = view.findViewById(R.id.acc_phone);
        linkedIn = view.findViewById(R.id.acc_linkedIn);
        funFact = view.findViewById(R.id.acc_funFact);
        unlockedCount = view.findViewById(R.id.acc_unlock_count);
        hideSID = view.findViewById(R.id.acc_switch_hideSID);
        disableFeatured = view.findViewById(R.id.acc_switch_disableFeatured);
        hideAccount = view.findViewById(R.id.acc_switch_hideAccount);
        btnEditProfile = view.findViewById(R.id.acc_edit_profile);
        btnLogout = view.findViewById(R.id.acc_logout);
        accountShimmer = view.findViewById(R.id.accountShimmer);
        accountShow = view.findViewById(R.id.accountShow);

        shimmerAnimation();

        mAuth = FirebaseAuth.getInstance();
        DatabaseHelper.getOne(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null) {
                    return;
                }
                currentUserProfile = snapshot.getValue(UserProfile.class);
                setProfileValues();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("getOne", "loadPost:onCancelled", error.toException());
            }
        });

        addSwitchListeners();

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent editIntent = new Intent(getContext(), EditProfile.class);
                editIntent.putExtra("profile_uid",currentUserProfile.getUid());
                editIntent.putExtra("profile_image",currentUserProfile.getProfPictUri());
                editIntent.putExtra("profile_name",currentUserProfile.getName());
                editIntent.putExtra("profile_major",currentUserProfile.getMajor());
                editIntent.putExtra("profile_faculty",currentUserProfile.getFaculty());
                editIntent.putExtra("profile_cohort",currentUserProfile.getCohort());
                editIntent.putExtra("profile_Id",(currentUserProfile.getSid()));
                editIntent.putExtra("profile_Ig",currentUserProfile.getInstagram());
                editIntent.putExtra("profile_email",currentUserProfile.getEmail());
                editIntent.putExtra("profile_phone",currentUserProfile.getPhoneNum());
                editIntent.putExtra("profile_linkedin",currentUserProfile.getLinkedIn());
                editIntent.putExtra("profile_funFact",currentUserProfile.getFunFact());
                getContext().startActivity(editIntent);
            }
        });

        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                            Intent intent = new Intent(getContext(), SignIn.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            v.getContext().startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        });

        return view;
    }

    private void addSwitchListeners() {
        List<Switch> switchList = Arrays.asList(new Switch[] {hideSID, disableFeatured, hideAccount});
        for (Switch s : switchList) {
            s.setOnClickListener((v) -> {
                HashMap<String, Object> switchUpdate = new HashMap<>();
                switchUpdate.put(s.getText().toString(), s.isChecked());
                DatabaseHelper.update(mAuth.getCurrentUser().getUid(), switchUpdate).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                    }
                });
            });
        }
    }

    private void setProfileValues() {
        Glide.with(getContext()).load(currentUserProfile.getProfPictUri()).placeholder(R.drawable.black_profile_picture).dontAnimate().into(profImg);
        name.setText(currentUserProfile.getName().isEmpty() ? "--" : currentUserProfile.getName());
        major.setText(currentUserProfile.getMajor().isEmpty() ? "--" : currentUserProfile.getMajor() + " |");
        faculty.setText(currentUserProfile.getFaculty().isEmpty() ? "--" : currentUserProfile.getFaculty());
        cohort.setText(currentUserProfile.getCohort().isEmpty() ? "--" : currentUserProfile.getCohort());
        sid.setText(currentUserProfile.getSid().isEmpty() ? "--" : currentUserProfile.getSid());
        instagram.setText(currentUserProfile.getInstagram().isEmpty() ? "--" : currentUserProfile.getInstagram());
        email.setText(currentUserProfile.getEmail().isEmpty() ? "--" : currentUserProfile.getEmail());
        phoneNum.setText(currentUserProfile.getPhoneNum().isEmpty() ? "--" : currentUserProfile.getPhoneNum());
        linkedIn.setText(currentUserProfile.getLinkedIn().isEmpty() ? "--" : currentUserProfile.getLinkedIn());
        funFact.setText(currentUserProfile.getFunFact().isEmpty() ? "--" : currentUserProfile.getFunFact());
        unlockedCount.setText(Integer.toString(currentUserProfile.getUnlockedProfilesCount()));
        hideSID.setChecked(currentUserProfile.isHideSID());
        disableFeatured.setChecked(currentUserProfile.isDisableFeatured());
        hideAccount.setChecked(currentUserProfile.isHideAccount());
    }

    private void shimmerAnimation() {

        if(needshimmer||!connection.isConnectingToInternet()){
        accountShow.setVisibility(View.GONE);
        accountShimmer.setVisibility(View.VISIBLE);
        accountShimmer.startShimmer();

        Handler handler2 = new Handler();
        if (connection.isConnectingToInternet()) {
            handler2.postDelayed(() -> {
                accountShimmer.stopShimmer();
                accountShimmer.setVisibility(View.GONE);
                accountShow.setVisibility(View.VISIBLE);
            }, 1200);
        }
        else {
            handler2.postDelayed(()->{
                if(connection.getCloseDialog()){
                    shimmerAnimation();
                };
            },10000);
        }
    }else{
            accountShimmer.setVisibility(View.GONE);
            accountShimmer.stopShimmer();
            accountShow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("needshimmer",false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}