package com.teamsketches.mcgms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLogin extends AppCompatActivity {
    MaterialEditText phone, password;
    TextView FG;
    ProgressBar loading;
    Button btnGrantAccess;



    private static String URL_LOGIN = "https://iufmpgrm.oyostate.gov.ng/android/login.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        sessionManager = new SessionManager(this);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        loading = findViewById(R.id.loading);
        btnGrantAccess = findViewById(R.id.btnGrantAccess);
        FG = findViewById(R.id.forgot);

        btnGrantAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPhone = phone.getText().toString().trim();
                String mPassword = password.getText().toString();

                if (!mPhone.isEmpty() || !mPassword.isEmpty()){
                    Login(mPhone, mPassword);
                }else{
                    phone.setError("Input Phone Number");
                    password.setError("Input Password");
                }
            }
        });

        FG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopUP();
            }

            private void PopUP() {
                Intent up = new Intent(UserLogin.this, forgot_password.class);
                startActivity(up);
            }
        });
    }

    private void Login(final String phone, final String password) {
        loading.setVisibility(View.VISIBLE);
        btnGrantAccess.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String firstname = object.getString("firstname").trim();
                                    String lastname = object.getString("lastname").trim();
                                    String phone = object.getString("phone").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();
                                    String sign_up_date = object.getString("sign_up_date").trim();

                                    sessionManager.createSession(firstname, lastname, phone, email, id, sign_up_date);

                                    Toast.makeText(UserLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent gotoWelcomeActivity = new Intent(UserLogin.this, Welcome.class);
                                    gotoWelcomeActivity.putExtra("firstname", firstname);
                                    gotoWelcomeActivity.putExtra("lastname", lastname);
                                    gotoWelcomeActivity.putExtra("phone", phone);
                                    gotoWelcomeActivity.putExtra("email", email);
                                    gotoWelcomeActivity.putExtra("id", id);
                                    gotoWelcomeActivity.putExtra("sign_up_date", sign_up_date);
                                    startActivity(gotoWelcomeActivity);
                                    loading.setVisibility(View.GONE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserLogin.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnGrantAccess.setVisibility(View.VISIBLE);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserLogin.this, "Error" +error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btnGrantAccess.setVisibility(View.VISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}