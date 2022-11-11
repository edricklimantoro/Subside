package com.example.subside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private int loading_time = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        PrefManager prefManager = new PrefManager(getApplicationContext());
//        if(prefManager.isFirstTimeLaunch()){
//            prefManager.setFirstTimeLaunch(false);
//            startActivity(new Intent(SplashScreen.this, WelcomeSlider.class));
//            finish();
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home=new Intent(SplashScreen.this, WelcomeSlider.class);
                startActivity(home);
                finish();
            }
        },loading_time);
    }
}