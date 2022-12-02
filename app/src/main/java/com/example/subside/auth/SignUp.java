package com.example.subside.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subside.CreateProfile;
import com.example.subside.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputConfirmPassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private TextView textHaveAccount;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPassword = findViewById(R.id.input_confirmpassword);
        btnSignUp = findViewById(R.id.button_signup);
        progressBar = findViewById(R.id.progress_bar_signup);
        progressBar.setVisibility(View.INVISIBLE);

        btnSignUp.setOnClickListener(v -> {
            performSignUp();
        });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            launchCreateProfile();
        }

        textHaveAccount = findViewById(R.id.have_account);
        textHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });
    }

    private void performSignUp() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (!isValidEmail(email)) {
            inputEmail.setError("Invalid email address");
            inputEmail.requestFocus();
        } else if (password.isEmpty() || password.length() < 8) {
            inputPassword.setError("8 characters minimum");
            inputPassword.requestFocus();
        } else if (!password.equals(confirmPassword)) {
            inputConfirmPassword.setError("Passwords do not match");
            inputConfirmPassword.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        launchCreateProfile();
                    } else {
                        Toast.makeText(SignUp.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    private boolean isValidEmail(String email) {
        final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    private void launchCreateProfile() {
        Intent intent = new Intent(SignUp.this, CreateProfile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}