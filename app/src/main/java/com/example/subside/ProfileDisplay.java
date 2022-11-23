package com.example.subside;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.subside.main_activity_fragments.search.ItemModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileDisplay extends AppCompatActivity {

//    private static ArrayList<ItemModel> dataItem;

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

        Intent intent = getIntent();

        imageProfile.setImageResource(intent.getIntExtra("profile_image",0));
        nameProfile.setText(intent.getStringExtra("profile_name"));
        majorProfile.setText(intent.getStringExtra("profile_major"));
        idProfile.setText(intent.getStringExtra("profile_Id"));
        igProfile.setText(intent.getStringExtra("profile_Ig"));
        emailProfile.setText(intent.getStringExtra("profile_email"));
        phoneProfile.setText(intent.getStringExtra("profile_phone"));
        linkedinProfile.setText(intent.getStringExtra("profile_linkedin"));


    }
}
