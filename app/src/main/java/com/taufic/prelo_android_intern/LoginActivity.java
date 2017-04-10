package com.taufic.prelo_android_intern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
//    private Toolbar toolbar;
    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Initialize ActionBar */
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        TextView title = (TextView) findViewById(R.id.titleBar);
        Button loginBtn = (Button) findViewById(R.id.loginButton);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
//        title.setText("Login");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate()) {
                    Log.d(TAG, "login failed");
                    onLoginFailed();
                    return;
                } else {
                    userLogin();

                }
            }
        });
    }
    public void userLogin() {
        final String username = usernameText.getText().toString();
        final String password = passwordText.getText().toString();
        String url = "https://dev.prelo.id/api/auth/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    JSONObject responseObj = null;
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            responseObj = new JSONObject(response);
                            JSONObject dataObj = (JSONObject) responseObj.get("_data");
                            JSONObject profileObj = (JSONObject) dataObj.get("profile");
                            JSONObject addressObj = (JSONObject) dataObj.get("default_address");
                            if(dataObj.length() > 0){
                                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                intent.putExtra("username", dataObj.get("username").toString());
                                intent.putExtra("urlPhoto", profileObj.get("pict").toString());
                                intent.putExtra("email", dataObj.get("email").toString());
                                intent.putExtra("fullname", dataObj.get("fullname").toString());
                                intent.putExtra("subdistrict_name", profileObj.get("subdistrict_name").toString());
                                intent.putExtra("region_name", addressObj.get("region_name").toString());
                                intent.putExtra("province_name", addressObj.get("province_name").toString());
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"loginFailed",Toast.LENGTH_LONG ).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username_or_email", username);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean validate() {
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(getBaseContext(), "Username must not empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(getBaseContext(), "password must not empty", Toast.LENGTH_LONG).show();
            return false;
        } else {
            if(password.length() < 6) {
                Toast.makeText(getBaseContext(), "password must not least than 6 character",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
