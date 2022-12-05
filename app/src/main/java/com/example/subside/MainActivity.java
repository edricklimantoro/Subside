package com.example.subside;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.subside.main_activity_fragments.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.subside.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    static BottomNavigationView navView;
    public static boolean fromHometoSearch = false;

    public static String majorSelected="All";
    public static int fMajorspos = 0;
    public static String[] fMajors={"All","Accounting","Computer Science","English Education","Math Education", "Industrial Engineering",
            "Information System", "Management", "Mechanical Engineering", "Visual Communication Design"};

    public static void goToSearchMajorSet(int majorID) {
        navView.setSelectedItemId(R.id.navigation_search);
        fMajorspos = majorID;
        majorSelected = fMajors[majorID];
        fromHometoSearch = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public static void setA(){
        navView.setSelectedItemId(R.id.navigation_search);
        fromHometoSearch = true;
    }



}