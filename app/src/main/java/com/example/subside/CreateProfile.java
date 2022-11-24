package com.example.subside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.subside.db.Constants;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.db.UserProfile;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;


public class CreateProfile extends AppCompatActivity {

    private Spinner majorSpinner, facultySpinner;
    private ImageView profileImage;
    private EditText inputFullName, inputCohort, inputSID, inputInstagram, inputEmail, inputPhoneNum, inputLinkedIn, inputFunfact;
    private Button btnSubmit;

    private UserProfile userProfile;
    private FirebaseAuth mAuth;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        majorSpinner = findViewById(R.id.majorSpinner);
        facultySpinner = findViewById(R.id.facultySpinner);
        profileImage = findViewById(R.id.image_createProfile);
        inputFullName = findViewById(R.id.input_fullName);
        inputCohort = findViewById(R.id.input_cohort);
        inputSID = findViewById(R.id.input_sid);
        inputInstagram = findViewById(R.id.input_instagram);
        inputEmail = findViewById(R.id.input_email);
        inputPhoneNum = findViewById(R.id.input_phoneNum);
        inputLinkedIn = findViewById(R.id.input_linkedIn);
        inputFunfact = findViewById(R.id.input_funfact);

        mAuth = FirebaseAuth.getInstance();
        db = new DatabaseHelper();
        populateSpinner();
        initializeProfile();

        btnSubmit = findViewById(R.id.submitCreateProfile);
        btnSubmit.setOnClickListener(view -> {
            performSubmitProfile();
        });
    }

    private void populateSpinner() {
        ArrayAdapter<String> majorsDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Constants.MAJORS);
        majorSpinner.setAdapter(majorsDataAdapter);
        majorsDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> facultiesDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Constants.FACULTIES);
        facultySpinner.setAdapter(facultiesDataAdapter);
        facultiesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initializeProfile() {
        userProfile = new UserProfile();
        userProfile.setProfPictUri(Constants.DEFAULT_PROFILE_PICT_URI);

        db.add(mAuth.getCurrentUser().getUid(), userProfile).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(CreateProfile.this, "SUCCESS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CreateProfile.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSubmitProfile() {
        HashMap<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("name", inputFullName.getText().toString());
        userUpdates.put("major", majorSpinner.getSelectedItem().toString());
        userUpdates.put("faculty", facultySpinner.getSelectedItem().toString());
        userUpdates.put("cohort", inputCohort.getText().toString());
        userUpdates.put("sid", inputSID.getText().toString());
        userUpdates.put("instagram", inputInstagram.getText().toString());
        userUpdates.put("email", inputEmail.getText().toString());
        userUpdates.put("phoneNum", inputPhoneNum.getText().toString());
        userUpdates.put("linkedIn", inputLinkedIn.getText().toString());
        userUpdates.put("funFact", inputFunfact.getText().toString());

        db.update(mAuth.getCurrentUser().getUid(), userUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(CreateProfile.this, "Profile Setup Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateProfile.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(CreateProfile.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}