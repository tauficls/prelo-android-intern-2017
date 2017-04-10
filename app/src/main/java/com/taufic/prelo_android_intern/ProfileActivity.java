package com.taufic.prelo_android_intern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

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
