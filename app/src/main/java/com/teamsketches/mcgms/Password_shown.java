package com.teamsketches.mcgms;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class Password_shown extends AppCompatActivity {
    SessionManage sessionManage;
    TextView password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_shown);

        password = findViewById(R.id.pass);

        sessionManage = new SessionManage(this);
        sessionManage.checkPassword();

        HashMap<String, String> user = sessionManage.getUserInfo();
        String mPassword = user.get(sessionManage.PASSWORD);

        password.setText(mPassword);



    }
}