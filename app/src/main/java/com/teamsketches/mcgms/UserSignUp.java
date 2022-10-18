package com.teamsketches.mcgms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class UserSignUp extends AppCompatActivity {

    // Defining my variables

    MaterialEditText fn, lastname, phone, email, password;
    ProgressBar loading;
    Button btnAddUser;

    private static String URL_SIGNUP = "https://iufmpgrm.oyostate.gov.ng/android/userreg.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        // Initialize our variables
        fn = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loading = findViewById(R.id.loading);
        btnAddUser = findViewById(R.id.btnAddUser);

        // Validation of variables
       btnAddUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String mFirstname = fn.getText().toString().trim();
               String mLastname = lastname.getText().toString().trim();
               String mPhone = phone.getText().toString().trim();
               String mEmail = email.getText().toString().trim();
               String mPassword = password.getText().toString().trim();

               if(!mFirstname.isEmpty() && !mLastname.isEmpty() && !mPhone.isEmpty() && !mEmail.isEmpty() && !mPassword.isEmpty()){
                   ProceedRegistration();
               }else{
                   fn.setError("Please type First Name");
                   lastname.setError("Please type Last Name");
                   phone.setError("Please type Phone Number");
                   email.setError("Please type Email");
                   password.setError("Please type Password");
               }
           }
       });
    }

    private void ProceedRegistration() {
        loading.setVisibility(View.VISIBLE);
        btnAddUser.setVisibility(View.GONE);

        final String firstname = this.fn.getText().toString().trim();
        final String lastname = this.lastname.getText().toString().trim();
        final String phone = this.phone.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(UserSignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent gotoLoginActivity = new Intent(UserSignUp.this, UserLogin.class);
                                startActivity(gotoLoginActivity);
                                loading.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserSignUp.this, "Registration Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnAddUser.setVisibility(View.VISIBLE);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserSignUp.this, "Registration Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btnAddUser.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("phone", phone);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}