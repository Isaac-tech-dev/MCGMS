package com.teamsketches.mcgms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    TextView firstname, lastname;
    SessionManager sessionManager;
    CardView edit;
    CircleImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edit = findViewById(R.id.edit);
        img = findViewById(R.id.image);

        //INTENT START

        //Profile start
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent,3);

//                Intent get = new Intent(Intent.ACTION_GET_CONTENT);
//                get.setType("image/*");
//
//                Intent pick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                pick.setType("image/*");
//
//                Intent chooser = Intent.createChooser(get, "Select Image");
//                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pick});
//
//                startActivityForResult(chooser, 3);
//
//            }
//        });

        //Profile end
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(Profile.this, Editprofile.class);
                startActivity(edit);
            }
        });

        //Initialize the assigned value
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Create Ticket selected
      bottomNavigationView.setSelectedItemId(R.id.profile);

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

                    case R.id.out:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });


        //Bottom menu ends here ..............................................


    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK && data != null){
//            Uri selectedImage = data.getData();
//            img.setImageURI(selectedImage);
//        }
//    }
}