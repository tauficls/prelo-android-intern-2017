package com.taufic.prelo_android_intern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.taufic.prelo_android_intern.Adapter.DataAdapter;
import com.taufic.prelo_android_intern.Item.DataItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * This class represents Profile section and Lovelist section.
 */
public class ProfileActivity extends AppCompatActivity {

    private ArrayList<DataItem> data;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String fullname = intent.getStringExtra("fullname");
        String email = intent.getStringExtra("email");
        String subdistrict_name = intent.getStringExtra("subdistrict_name");
        String region_name = intent.getStringExtra("region_name");
        String province_name = intent.getStringExtra("province_name");
        String urlPhoto = intent.getStringExtra("urlPhoto");
        token = intent.getStringExtra("token");
        System.out.println(token);
        /* action bar */
        getSupportActionBar().setTitle(username);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* load image */
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        Glide.with(this).load(urlPhoto).into(circleImageView);

        /* set textview */
        TextView fullnameText = (TextView) findViewById(R.id.fullname);
        TextView usernameText = (TextView) findViewById(R.id.user_name);
        TextView emailText = (TextView) findViewById(R.id.email);
        TextView addressText = (TextView) findViewById(R.id.address);
        fullnameText.setText(fullname);
        usernameText.setText(username);
        emailText.setText(email);
        addressText.setText(subdistrict_name + ", " + region_name + ", " + province_name);

        grabData();

        data = new ArrayList<>();
        
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        DataAdapter dataAdapter = new DataAdapter(this, data);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dataAdapter);
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

    /**
     * Retrieving lovelist data from api using GET method.
     */
    public void grabData() {
        String url = "https://dev.prelo.id/api/me/lovelist";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    JSONObject responseObj = null;
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            responseObj = new JSONObject(response);
                            JSONArray dataArray = (JSONArray) responseObj.get("_data");
                            if(dataArray.length() > 0){
                                for(int i = 0; i < dataArray.length(); i++) {
                                    DataItem dataItem = new DataItem();
                                    JSONObject dataObj = (JSONObject) dataArray.get(i);
                                    dataItem.setName(dataObj.get("name").toString());
                                    dataItem.setCost("Rp " + dataObj.get("price").toString());
                                    JSONArray picArray = (JSONArray) dataObj.get("display_picts");
                                    dataItem.setUrlPhoto(picArray.get(0).toString());
                                    dataItem.setId(dataObj.get("_id").toString());
                                    data.add(dataItem);
                                }

                            }else{
                                Toast.makeText(ProfileActivity.this,"no Data",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this,"Failed Retrieve Data",Toast.LENGTH_LONG ).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
