package com.example.subside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.subside.db.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.HashMap;

public class ProfileDisplay extends AppCompatActivity {

//    private static ArrayList<ItemModel> dataItem;
    Intent intent;
    TextView descGuessFunFact;
    TextInputLayout inputGuessFunFactLayout;
    TextInputEditText inputGuessFunFact;
    TextView funFactProfile;
    Button btnSubmitGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);

        ImageView imageProfile = findViewById(R.id.profile_image);
        TextView nameProfile = findViewById(R.id.profile_name);
        TextView majorProfile = findViewById(R.id.profile_major);
        TextView idProfile = findViewById(R.id.profile_id);
        TextView igProfile = findViewById(R.id.profile_ig);
        TextView emailProfile = findViewById(R.id.profile_email);
        TextView phoneProfile = findViewById(R.id.profile_phone);
        TextView linkedinProfile = findViewById(R.id.profile_linkedin);
        descGuessFunFact = findViewById(R.id.desc_funfact_guess);
        inputGuessFunFactLayout = findViewById(R.id.layout_guess_funfact);
        inputGuessFunFact = findViewById(R.id.input_guess_funfact);
        funFactProfile = findViewById(R.id.text_funfact);
        btnSubmitGuess = findViewById(R.id.submitGuessFunFact);

        intent = getIntent();

    //    imageProfile.setImageResource(intent.getIntExtra("profile_image",0));
        nameProfile.setText(intent.getStringExtra("profile_name"));
        majorProfile.setText(intent.getStringExtra("profile_major"));
        idProfile.setText(intent.getStringExtra("profile_Id"));
        igProfile.setText(intent.getStringExtra("profile_Ig"));
        emailProfile.setText(intent.getStringExtra("profile_email"));
        phoneProfile.setText(intent.getStringExtra("profile_phone"));
        linkedinProfile.setText(intent.getStringExtra("profile_linkedin"));
        funFactProfile.setText(intent.getStringExtra("profile_funFact"));

        if (isProfileUnlocked()) {
            unlockFunFact();
        } else {
            lockFunFact();
            btnSubmitGuess.setOnClickListener(view -> {
                if (isFunFactGuessCorrect()) {
                    Toast.makeText(ProfileDisplay.this, "You've guessed correctly!", Toast.LENGTH_SHORT).show();
                    unlockFunFact();
                    updateUserUnlockedProfiles();
                } else {
                    Toast.makeText(ProfileDisplay.this, "Incorrect guess! (note: enter the exact same text)", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateUserUnlockedProfiles() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String newUnlockedProfiles = intent.getStringExtra("user_unlocked_profiles") + intent.getStringExtra("profile_uid") + ",";
        Integer newCount = Arrays.asList(newUnlockedProfiles.split("\\s*,\\s*")).size();

        HashMap<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("unlockedProfiles", newUnlockedProfiles);
        userUpdates.put("unlockedProfilesCount", newCount);

        DatabaseHelper.update(mAuth.getCurrentUser().getUid(), userUpdates);
    }

    private void unlockFunFact() {
        descGuessFunFact.setVisibility(View.GONE);
        inputGuessFunFactLayout.setVisibility(View.GONE);
        btnSubmitGuess.setVisibility(View.GONE);
        funFactProfile.setVisibility(View.VISIBLE);
    }

    private void lockFunFact() {
        descGuessFunFact.setVisibility(View.VISIBLE);
        inputGuessFunFactLayout.setVisibility(View.VISIBLE);
        btnSubmitGuess.setVisibility(View.VISIBLE);
        funFactProfile.setVisibility(View.GONE);
    }

    private boolean isFunFactGuessCorrect() {
        return inputGuessFunFact.getText().toString().equalsIgnoreCase(intent.getStringExtra("profile_funFact"));
    }

    private boolean isProfileUnlocked() {
        Log.i("USER UNLOCKED PROFIELS:", intent.getStringExtra("user_unlocked_profiles"));
        Log.i("PROFILE UID::::::", intent.getStringExtra("profile_uid"));
        return intent.getStringExtra("user_unlocked_profiles").contains(intent.getStringExtra("profile_uid"));
    }
}
