package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.parkhere.android.CreateListingStartTimeActivity.instance;

public class CreateListingFinalActivity extends AppCompatActivity {

    Button okay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing_final);

        okay = findViewById(R.id.okay_button);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(CreateListingConfirmActivity.instance != null) {
            try {
                CreateListingConfirmActivity.instance.finish();
            } catch (Exception e) {}
        }
        if(CreateListingMapsActivity.instance != null) {
            try {
                CreateListingMapsActivity.instance.finish();
            } catch (Exception e) {}
        }
        if(CreateListingDetailsActivity.instance != null) {
            try {
                CreateListingDetailsActivity.instance.finish();
            } catch (Exception e) {}
        }
        if(CreateListingStartDateActivity.instance != null) {
            try {
                CreateListingStartDateActivity.instance.finish();
            } catch (Exception e) {}
        }
        if(CreateListingStartTimeActivity.instance != null) {
            try {
                CreateListingStartTimeActivity.instance.finish();
            } catch (Exception e) {}
        }
        if(CreateListingEndDateActivity.instance != null) {
            try {
                CreateListingEndDateActivity.instance.finish();
            } catch (Exception e) {}
        }
        if(CreateListingEndTimeActivity.instance != null) {
            try {
                CreateListingEndTimeActivity.instance.finish();
            } catch (Exception e) {}
        }

    }
}