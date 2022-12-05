package com.example.subside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.subside.db.Constants;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.main_activity_fragments.account.AccountFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private Spinner majorSpinner, facultySpinner;
    private CircleImageView profileImage;
    private EditText inputFullName, inputCohort, inputSID, inputInstagram, inputEmail, inputPhoneNum, inputLinkedIn, inputFunfact;
    private Button btnSubmit, btnCancel;

    private final List<String> MAJORS = Arrays.asList(Constants.MAJORS);
    private final List<String> FACULTIES = Arrays.asList(Constants.FACULTIES);

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

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
        profileImage = findViewById(R.id.image_editProfile);

        populateSpinner();

        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra("profile_image")).placeholder(R.drawable.black_profile_picture).dontAnimate().into(profileImage);
        inputFullName.setText(intent.getStringExtra("profile_name"));
        majorSpinner.setSelection(MAJORS.indexOf(intent.getStringExtra("profile_major")));
        facultySpinner.setSelection(FACULTIES.indexOf(intent.getStringExtra("profile_faculty")));
        inputCohort.setText(intent.getStringExtra("profile_cohort"));
        inputSID.setText(intent.getStringExtra("profile_Id"));
        inputInstagram.setText(intent.getStringExtra("profile_Ig"));
        inputEmail.setText(intent.getStringExtra("profile_email"));
        inputPhoneNum.setText(intent.getStringExtra("profile_phone"));
        inputLinkedIn.setText(intent.getStringExtra("profile_linkedin"));
        inputFunfact.setText(intent.getStringExtra("profile_funFact"));

        btnSubmit = findViewById(R.id.submitEditProfile);
        mAuth = FirebaseAuth.getInstance();
        btnSubmit.setOnClickListener(view -> {
            performSubmitProfile();
        });
        btnCancel = findViewById(R.id.cancelEditProfile);
        btnCancel.setOnClickListener( view -> {
            finish();
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

    private void performSubmitProfile() {
        HashMap<String, Object> userUpdates = new HashMap<>();
        // TODO: Update profile image
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

        DatabaseHelper.update(mAuth.getCurrentUser().getUid(), userUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditProfile.this, "Profile Update Successful", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EditProfile.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}