package com.example.aichatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DBHelper mydb = new DBHelper(this);
    Button submit_button;
    EditText login_name, login_pass;
    String name, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit_button = findViewById(R.id.login_submit);
        login_name = findViewById(R.id.login_name);
        login_pass = findViewById(R.id.login_pass);
        name = login_name.getText().toString();
        password = login_pass.getText().toString();
        submit_button.setOnClickListener(v -> {
            if(mydb.loginSuccess(name, password)) {
                Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ChatBotActivity.class);//go to login activity
                startActivity(intent);
            }
            else Toast.makeText(this, "Wrong name or password", Toast.LENGTH_SHORT).show();
        });
    }
}