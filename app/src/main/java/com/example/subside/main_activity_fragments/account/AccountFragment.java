package com.example.subside.main_activity_fragments.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.subside.R;
import com.example.subside.auth.SignIn;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.db.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    private UserProfile currentUserProfile;
    private FirebaseAuth mAuth;
    private TextView name, major, faculty, cohort, sid, instagram, email, phoneNum, linkedIn, funFact, unlockedCount;
    private Switch showSID, allowFeatured, showAccount;
    private TextView btnEditProfile, btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

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
        showSID = view.findViewById(R.id.acc_switch_showSID);
        allowFeatured = view.findViewById(R.id.acc_switch_allowFeatured);
        showAccount = view.findViewById(R.id.acc_switch_showAccount);
        btnLogout = view.findViewById(R.id.acc_logout);

        mAuth = FirebaseAuth.getInstance();
        DatabaseHelper.getOne(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUserProfile = snapshot.getValue(UserProfile.class);
                setProfileValues();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("getOne", "loadPost:onCancelled", error.toException());
            }
        });

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(getContext(), SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        });

        return view;
    }

    private void setProfileValues() {
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
        showSID.setChecked(!currentUserProfile.isHideSID());
        allowFeatured.setChecked(!currentUserProfile.isDisableFeatured());
        showAccount.setChecked(!currentUserProfile.isHideAccount());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}