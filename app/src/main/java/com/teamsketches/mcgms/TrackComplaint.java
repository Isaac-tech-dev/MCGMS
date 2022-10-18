package com.teamsketches.mcgms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackComplaint extends AppCompatActivity {

    //Defining Variables
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<show> productList = new ArrayList<>();
    SessionManager sessionManager;
    String fid,fn,ln;
    String cat;
    String iss;
    private static String url_get ="https://iufmpgrm.oyostate.gov.ng/android/complaint_read.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_complaint);

        Intent recieve = getIntent();
        String recieved = recieve.getStringExtra("KEY_SENDER");


        //Initialize the assigned value
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Complaints selected
        bottomNavigationView.setSelectedItemId(R.id.complaintLog);

        recyclerView = (RecyclerView) findViewById(R.id.recycle);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);



        recyclerView.setHasFixedSize(true);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String,String> user = sessionManager.getUserInfo();
        fid = user.get(sessionManager.ID);
        fn = user.get(sessionManager.FIRSTNAME);
        ln = user.get(sessionManager.LASTNAME);
        String id = fid;
        String fnn = fn;
        String lnn = ln;


//        if(getIntent() != null){
//            cat = getIntent().getStringExtra("comp_category");
//            iss = getIntent().getStringExtra("issue_name");
//        }

        StringRequest track = new StringRequest(Request.Method.POST, url_get, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);

//                        String complaincategory = object.getString("comp_category").trim();
//                        String issuetype = object.getString("issue_name").trim();
                        String complain = object.getString("c_message").trim();

                        show show = new show(complain);
                        productList.add(show);

                    }
                    adapter = new Adapter(productList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    Toast.makeText(TrackComplaint.this, "Error!!!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TrackComplaint.this, "Error!!!!!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> trace = new HashMap<>();
                trace.put("sent_by", id);
                trace.put("c_firstname", fnn);
                trace.put("c_lastname",lnn);
                return trace;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(track);

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
                        finish();
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
