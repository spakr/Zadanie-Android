package com.roman.zadanie;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.roman.zadanie.databinding.ActivityMainBinding;
import com.roman.zadanie.db.MyDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;


    private NavController navController;


    public MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        navController = Navigation.findNavController(this,R.id.nav_host_fragment);


        NavigationUI.setupWithNavController(binding.bottomNav,navController);


        database = MyDatabase.getInstance(this);

    }

}