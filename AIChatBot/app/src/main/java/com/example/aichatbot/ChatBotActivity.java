package com.example.aichatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ChatBotActivity extends AppCompatActivity {
    LinearLayout ll;
    View user_message, bot_message;
    Button button, chatbot_send;
    EditText chatbot_message;
    String message, res;
    ScrollView sv;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        String filePath = "C:\\Users\\91828\\AndroidStudioProjects\\AIChatBot\\app\\src\\main\\assets" + "\\log.txt";
        Log.d("HERE", filePath);
        mydb = new DBHelper(this);
        //mydb.addUser("Ankita", "C/15", "8280292201", "Alok",
        //"father", "8895500150", "Sujata", "mother", "7750958499", "ankita");
        //setting up Send button
        {
            ll = findViewById(R.id.chatbot_ll);
            chatbot_message = findViewById(R.id.chatbot_message);
            chatbot_send = findViewById(R.id.chatbot_send);
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(this));
            }
            Python python = Python.getInstance();
            PyObject pythonFile = python.getModule("home");
            chatbot_send.setOnClickListener(v -> {
                user_message = LayoutInflater.from(getApplicationContext()).inflate(R.layout.message_layout_user, null);
                bot_message = LayoutInflater.from(getApplicationContext()).inflate(R.layout.message_layout_bot, null);
                message = chatbot_message.getText().toString();
                button = user_message.findViewById(R.id.message);
                button.setText(message);
                ll.addView(user_message);
                chatbot_message.setText("");
                button.setGravity(Gravity.RIGHT);
                //SystemClock.sleep(1300);
                button = bot_message.findViewById(R.id.message);
                {
                    {
                        PyObject response = pythonFile.callAttr("response", message.toString());
                        //writing conversation to file log.txt
                        try {
                            FileWriter fr = new FileWriter(filePath, true);
                            //fr.write("USER:"+message+"\n"+"BOT:"+response);
                            fr.write("dcfgvmkl");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("Exception","File IO error");
                        }
                        button.setText(response.toString());
                    }
                }
                //button.setText("Hello");
                ll.addView(bot_message);
                button.setGravity(Gravity.LEFT);
            });
        }
        //code to implement Python AIML code via Chaquopy
    }
}