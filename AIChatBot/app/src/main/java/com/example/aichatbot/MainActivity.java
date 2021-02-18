package com.example.aichatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button login_button, sign_up_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button = findViewById(R.id.login_button);
        sign_up_button = findViewById(R.id.sign_up_button);
        login_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);//go to login activity
            startActivity(intent);
        });
        sign_up_button.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);//go to sign up activity
            startActivity(intent);
        });
    }
}