
package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BrowseListingFinalActivity extends AppCompatActivity {

    private Button okay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_listing_final);
        okay = findViewById(R.id.okay_button);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseListingFinalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}