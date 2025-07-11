package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            // Arahkan ke CategorySelectionActivity sebagai pengganti MainActivity
            Intent intent = new Intent(SplashActivity.this, CategorySelectionActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}