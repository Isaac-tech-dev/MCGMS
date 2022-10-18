package com.teamsketches.mcgms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    
    Button btnSignIn, btnRegister;
    RelativeLayout rootLayout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Init View
        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnSignIn);
        rootLayout = findViewById(R.id.rootlayout);

//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OpenUserLoginActivity();
//            }
//        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserLogin.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                OpenUserSignUpActivity();
            }
        });
    }

//    private void gotoUserLogin() {
//        Intent connectToUserLogin =
//    }

//    private void OpenUserLoginActivity() {
//        Intent intent = new Intent(this, UserLogin.class);
//        startActivity(intent);
//    }

    private void OpenUserSignUpActivity() {
        Intent intent = new Intent(this, UserSignUp.class);
        startActivity(intent);
        finish();
    }

}