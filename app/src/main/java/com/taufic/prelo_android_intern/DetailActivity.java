package com.taufic.prelo_android_intern;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by taufic on 4/20/2017.
 */

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        grabData();


    }

    void grabData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("product_id");
        String url = "https://dev.prelo.id/api/product/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    JSONObject responseObj = null;
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
//                        try {
//                            /*ystem.out.println(response);
//                            responseObj = new JSONObject(response);
//                            JSONArray dataArray = (JSONArray) responseObj.get("_data");
//                            if(dataArray.length() > 0){
//                                for(int i = 0; i < dataArray.length(); i++) {
//                                    DataItem dataItem = new DataItem();
//                                    JSONObject dataObj = (JSONObject) dataArray.get(i);
//                                    dataItem.setName(dataObj.get("name").toString());
//                                    dataItem.setCost("Rp " + dataObj.get("price").toString());
//                                    JSONArray picArray = (JSONArray) dataObj.get("display_picts");
//                                    dataItem.setUrlPhoto(picArray.get(0).toString());
//                                    dataItem.setId(dataObj.get("_id").toString());
//                                    data.add(dataItem);*/
////                                }
//
////                            }else{
////                                Toast.makeText(DetailActivity.this,"no Data",Toast.LENGTH_LONG).show();
////                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailActivity.this, "Failed Retrieve Data", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
