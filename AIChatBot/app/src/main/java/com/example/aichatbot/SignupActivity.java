package com.example.aichatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    DBHelper mydb = new DBHelper(this);
    Button signup_submit;
    EditText signup_name, signup_pass, signup_addr, signup_phone, signup_mem1, signup_rel1, signup_phon1, signup_mem2, signup_rel2, signup_phon2;
    String name, password, address, phone, mem1, rel1, phon1, mem2, rel2, phon2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        {
            signup_submit = findViewById(R.id.signup_submit);
            signup_name = findViewById(R.id.signup_name);
            signup_pass = findViewById(R.id.signup_pass);
            signup_addr = findViewById(R.id.signup_addr);
            signup_phone = findViewById(R.id.signup_phone);
            signup_mem1 = findViewById(R.id.signup_mem1);
            signup_rel1 = findViewById(R.id.signup_rel1);
            signup_phon1 = findViewById(R.id.signup_phon1);
            signup_mem1 = findViewById(R.id.signup_mem2);
            signup_rel1 = findViewById(R.id.signup_rel2);
            signup_phon1 = findViewById(R.id.signup_phon2);
            name = signup_name.getText().toString();
            password = signup_pass.getText().toString();
            address = signup_addr.getText().toString();
            phone = signup_phone.getText().toString();
            mem1 = signup_mem1.getText().toString();
            rel1 = signup_rel1.getText().toString();
            phon1 = signup_phon1.getText().toString();
            mem2 = signup_mem2.getText().toString();
            rel2 = signup_rel2.getText().toString();
            phon2 = signup_phon2.getText().toString();
        }
        if(mem2.equals("")){
            mem2="NULL";
            rel2="NULL";
            phon2="NULL";
        }
        if(mydb.addUser(name, address, phone, mem1, rel1, phon1, mem2, rel2, phon2, password)) {
            Toast.makeText(this, "Successfully added new user", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ChatBotActivity.class);
            startActivity(intent);
        }
    }
}