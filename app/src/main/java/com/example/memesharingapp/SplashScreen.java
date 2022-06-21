package com.example.memesharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // code for Splash Screen
        SystemClock.sleep(2000);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
