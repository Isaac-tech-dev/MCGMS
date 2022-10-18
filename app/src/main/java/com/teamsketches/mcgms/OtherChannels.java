package com.teamsketches.mcgms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OtherChannels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_channels);



        //Initialize the assigned value
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Create Ticket selected
       // bottomNavigationView.setSelectedItemId(R.id.inboxId);


        //Bottom menu starts here ............................................

        //Perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,Welcome.class));
                        overridePendingTransition(0,0);
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


    }
}