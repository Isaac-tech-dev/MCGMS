package com.teamsketches.mcgms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class Welcome extends AppCompatActivity {

    CardView complaint, whatsapp, otherChannels,call;
    TextView firstname, lastname;
    SessionManager sessionManager;
    String phone = "+234 8004836742";

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        complaint = findViewById(R.id.complain);
        whatsapp = findViewById(R.id.whatsapp);
        otherChannels = findViewById(R.id.otherChannels);
        call = findViewById(R.id.call);

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.side_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.track:
                        Intent tracking = new Intent(Welcome.this, TrackComplaint.class);
                        startActivity(tracking);
                        return true;

                    case R.id.inbox:
                        Intent message = new Intent(Welcome.this, Inbox.class);
                        startActivity(message);
                        return true;

                    case R.id.about:
                        Intent about = new Intent(Welcome.this, About_us.class);
                        startActivity(about);
                        return true;
                }
                return false;
            }
        });


        // Create Intents start here ..............................................

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCreateTicketActivity = new Intent(Welcome.this, ComplaintLog.class);
                startActivity(gotoCreateTicketActivity);
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean installed = isAppInstalled("com.whatsapp");

                if(installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+phone));
                    startActivity(intent);
                }else{
                    Toast.makeText(Welcome.this, "Whatsapp is not installed!!!!!", Toast.LENGTH_SHORT).show();
                }
//                Uri uri = Uri.parse("smsto" + phone);
//                Intent msg = new Intent(Intent.ACTION_SENDTO, uri);
//                msg.setPackage("com.whatsapp");
//                startActivity(msg);
            }
        });

        otherChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoOtherChannelActivity = new Intent(Welcome.this, OtherChannels.class);
                startActivity(gotoOtherChannelActivity);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ phone));
                startActivity(intent);
            }
        });

        // Create Intents ends here ..............................................

        //Initialize the assigned value
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);



        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);

        HashMap<String, String> user = sessionManager.getUserInfo();
        String mFirstname = user.get(sessionManager.FIRSTNAME);
        String mLastname = user.get(sessionManager.LASTNAME);

        firstname.setText(mFirstname);
        lastname.setText(mLastname);


        //Bottom menu starts here ............................................

        //Perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.complaintLog:
                        startActivity(new Intent(getApplicationContext()
                                ,ComplaintLog.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.inboxId:
                        startActivity(new Intent(getApplicationContext()
                                ,Inbox.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext()
                                ,Profile.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.out:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //Bottom menu ends here ..............................................


        //Navigation Starts here


    }

    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getPackageManager();
        boolean is_installed;

        try{
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
    }

    @SuppressLint("WrongViewCast")
    private void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}