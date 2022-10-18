package com.teamsketches.mcgms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
    MaterialEditText fn,ln,em,ph;
    ProgressBar loading;
    Button btn;
    SessionManager sessionManager;
    String fid;

    private static String url_edit = "https://iufmpgrm.oyostate.gov.ng/android/edit_profile_data_sam.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        sessionManager = new SessionManager(this);

        fn = findViewById(R.id.firstname);
        ln = findViewById(R.id.lastname);
        em = findViewById(R.id.email);
        ph = findViewById(R.id.phone);
        loading = findViewById(R.id.loading);
        btn = findViewById(R.id.btnAddUser);

        //Initialize the assigned value
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Complaints selected
        bottomNavigationView.setSelectedItemId(R.id.complaintLog);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mFirstname = fn.getText().toString().trim();
                String mLastname = ln.getText().toString().trim();
                String mPhone = ph.getText().toString().trim();
                String mEmail = em.getText().toString().trim();

                if(!mFirstname.isEmpty() && !mLastname.isEmpty() && !mPhone.isEmpty() && !mEmail.isEmpty()){
                    Edit();
                }else{
                    fn.setError("Please type First Name");
                    ln.setError("Please type Last Name");
                    ph.setError("Please type Phone Number");
                    em.setError("Please type Email");
                }

            }
        });
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserInfo();
        String mFirstname = user.get(sessionManager.FIRSTNAME);
        String mLastname = user.get(sessionManager.LASTNAME);
        String mEmail = user.get(sessionManager.EMAIL);
        String mPhone = user.get(sessionManager.PHONE);
        fid = user.get(sessionManager.ID);

        fn.setText(mFirstname);
        ln.setText(mLastname);
        em.setText(mEmail);
        ph.setText(mPhone);

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


    private void Edit(){
        final String firstname = this.fn.getText().toString().trim();
        final String lastname = this.ln.getText().toString().trim();
        final String phone = this.ph.getText().toString().trim();
        final String email = this.em.getText().toString().trim();
        String id = fid;


        loading.setVisibility(View.VISIBLE);
        btn.setVisibility(View.GONE);

        StringRequest request = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                            Toast.makeText(Editprofile.this, "Edit Successful", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn.setVisibility(View.VISIBLE);
                    }


                }catch (JSONException e){
                    Toast.makeText(Editprofile.this, "Error!!!!!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                    btn.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Editprofile.this, "Error!!!!!!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> edit = new HashMap<>();
                edit.put("firstname",firstname);
                edit.put("lastname", lastname);
                edit.put("email", email);
                edit.put("phone", phone);
                edit.put("id", id);
                return edit;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}