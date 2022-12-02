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

import com.example.subside.MainActivity;
import com.example.subside.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn;
    private ProgressBar progressBar;
    private TextView textNoAccount;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnSignIn = findViewById(R.id.button_signin);
        progressBar = findViewById(R.id.progress_bar_signin);
        progressBar.setVisibility(View.INVISIBLE);

        btnSignIn.setOnClickListener(v -> {
            performSignIn();
        });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            launchMainActivity();
        }

        textNoAccount = findViewById(R.id.no_account);
        textNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });
    }

    private void performSignIn() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (!isValidEmail(email)) {
            inputEmail.setError("Invalid email address");
            inputEmail.requestFocus();
        } else if (password.isEmpty() || password.length() < 8) {
            inputPassword.setError("8 characters minimum");
            inputPassword.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignIn.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                        launchMainActivity();
                    } else {
                        Toast.makeText(SignIn.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
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

    private void launchMainActivity() {
        Intent intent = new Intent(SignIn.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}