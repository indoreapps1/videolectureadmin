package com.example.videolectureadmin.activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.videolectureadmin.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        int a = sharedPreferences.getInt("id", 0);
        if (a!=0){

        }
    }
}
