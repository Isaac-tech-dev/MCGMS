package com.teamsketches.mcgms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplaintLog extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText comp;
    ProgressBar loading;
    Button btn, ChooseBn;
    ImageView imageView;

    private final int IMG_REQUEST = 1;
    Bitmap bitmap;


    Spinner spinnercomplain, spinnerissue, spinnerproject, spinnerlocation, spinnerchannel;
    ArrayList<String> complainlist = new ArrayList<>();
    ArrayList<String> issuelist = new ArrayList<>();
    ArrayList<String> projectlist = new ArrayList<>();
    ArrayList<String> locationlist = new ArrayList<>();
    ArrayList<String> channellist = new ArrayList<>();
    ArrayAdapter<String> complainadapter, issueadapter, projectadapter, locationadapter, channeladapter;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String fid,fn,ln;
    String category;
    String issuetype;

    private static String url_comp = "https://iufmpgrm.oyostate.gov.ng/android/complaint.php";

    private static String url ="https://iufmp.spotters.tech/android/image_upload";


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_log);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        spinnercomplain = findViewById(R.id.comp);
        spinnerissue = findViewById(R.id.issues2);
        spinnerlocation = findViewById(R.id.location2);
        spinnerproject = findViewById(R.id.project2);
        spinnerchannel = findViewById(R.id.channel2);

        ChooseBn = findViewById(R.id.choosebn);

        ChooseBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        //Endpoints
        String url_complain ="https://iufmpgrm.oyostate.gov.ng/android/spinner_options_comp_category.php";
        String url_issue = "https://iufmpgrm.oyostate.gov.ng/android/spinner_options_issue_type.php";
        String url_location = "https://iufmpgrm.oyostate.gov.ng/android/spinner_options_locations.php";
        String url_project = "https://iufmpgrm.oyostate.gov.ng/android/spinner_options_project.php";
        String url_channel = "https://iufmpgrm.oyostate.gov.ng/android/spinner_options_complaint_channel.php";


        //SPINNER PICKER
//        CharSequence spinner1selectedData = (CharSequence) spinnercomplain.getSelectedItem();
//        category = (String) spinner1selectedData;
//        CharSequence spinner2selectedData = (CharSequence) spinnerissue.getSelectedItem();
//        issuetype = (String) spinner2selectedData;


        //..................................Spinner Begins

        //..................................Complain Spinner
        JsonObjectRequest complainjsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_complain, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("complaint_cat");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String complainname = jsonObject.optString("comp_category");
                        complainlist.add(complainname);
                        complainadapter = new ArrayAdapter<>(ComplaintLog.this, android.R.layout.simple_spinner_item, complainlist);
                        complainadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnercomplain.setAdapter(complainadapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ComplaintLog.this, "Error!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintLog.this, "Error!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(complainjsonObjectRequest);
        //..................................Complain Spinner

        //..................................Issue Spinner
        JsonObjectRequest issuejsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_issue, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("issue_type");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String issuename = jsonObject.optString("issue_name");
                        issuelist.add(issuename);
                        issueadapter = new ArrayAdapter<>(ComplaintLog.this, android.R.layout.simple_spinner_item, issuelist);
                        issueadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerissue.setAdapter(issueadapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ComplaintLog.this, "Error!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintLog.this, "Error!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(issuejsonObjectRequest);
        //..................................Issue Spinner

        //..................................Location Spinner
        JsonObjectRequest locationjsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_location, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("all_locations");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String locationname = jsonObject.optString("location");
                        locationlist.add(locationname);
                        locationadapter = new ArrayAdapter<>(ComplaintLog.this, android.R.layout.simple_spinner_item, locationlist);
                        locationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerlocation.setAdapter(locationadapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ComplaintLog.this, "Error!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintLog.this, "Error!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(locationjsonObjectRequest);
        //..................................Location Spinner

        //..................................Project Spinner
        JsonObjectRequest projectjsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_project, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("iufmp_project");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String projectname = jsonObject.optString("project_name");
                        projectlist.add(projectname);
                        projectadapter = new ArrayAdapter<>(ComplaintLog.this, android.R.layout.simple_spinner_item, projectlist);
                        projectadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerproject.setAdapter(projectadapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ComplaintLog.this, "Error!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintLog.this, "Error!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(projectjsonObjectRequest);
        //..................................Project Spinner

        //.................................. Spinner
        JsonObjectRequest channeljsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_channel, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("complaint_channel");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String channelname = jsonObject.optString("channel_name");
                        channellist.add(channelname);
                        channeladapter = new ArrayAdapter<>(ComplaintLog.this, android.R.layout.simple_spinner_item, channellist);
                        channeladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerchannel.setAdapter(channeladapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ComplaintLog.this, "Error!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintLog.this, "Error!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(channeljsonObjectRequest);
        //..................................Channel Spinner

        //..................................Spinner Ends

        sessionManager = new SessionManager(this);

        comp = findViewById(R.id.complain1);
        btn = findViewById(R.id.button3);
        loading = findViewById(R.id.loading);




        //Button Action

        //Initialize the assigned value
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Complaints selected
        bottomNavigationView.setSelectedItemId(R.id.complaintLog);

        //Button action................................
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String complain = comp.getText().toString().trim();
                if(!complain.isEmpty()){
                    complain();
                    uploadImage();
                }else{
                    comp.setError("Fill in your complain");
                }
            }
        });

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

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String,String> user = sessionManager.getUserInfo();
        fid = user.get(sessionManager.ID);
        fn = user.get(sessionManager.FIRSTNAME);
        ln = user.get(sessionManager.LASTNAME);

    }





    private void complain(){
        loading.setVisibility(View.VISIBLE);
        btn.setVisibility(View.GONE);

        final String complaint = this.comp.getText().toString().trim();
        String id = fid;
        String fnn = fn;
        String lnn = ln;

        StringRequest request = new StringRequest(Request.Method.POST, url_comp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Intent complain = new Intent(ComplaintLog.this,TrackComplaint.class);
                        startActivity(complain);
                        finish();
                        Toast.makeText(ComplaintLog.this, "Complain Successful", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn.setVisibility(View.VISIBLE);

                        //Spinner transfer
//                        Intent move = new Intent(ComplaintLog.this, TrackComplaint.class);
//                        move.putExtra("comp_category", category.toString());
//                        move.putExtra("issue_name", issuetype.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ComplaintLog.this, "Errorr!!!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn.setVisibility(View.VISIBLE);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintLog.this, "Error!!!!!!!!" + error.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> complain = new HashMap<>();
                complain.put("c_message",complaint);
                complain.put("sent_by",id);
                complain.put("c_firstname",fnn);
                complain.put("c_lastname",lnn);
                return complain;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void buttonsender(View v){
        Intent send = new Intent(this,TrackComplaint.class);
        send.putExtra("KEY_SENDER", complainadapter.getContext().toString());
        startActivity(send);
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){

            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(ComplaintLog.this, Response, Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(0);
                    imageView.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> push = new HashMap<>();
                push.put("image",imageToString(bitmap));
                return super.getParams();
            }
        };
        MySingleton.getInstance(ComplaintLog.this).addToRequestQueue(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte [] imagebyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagebyte, Base64.DEFAULT);
    }
}