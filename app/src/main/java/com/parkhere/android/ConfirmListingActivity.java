package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmListingActivity extends AppCompatActivity {

    private Button confirm;
    private TextView price_text_view;
    private TextView description_text_view;
    private TextView spot_type_text_view;
    private TextView start_date_text_view;
    private TextView end_date_text_view;
    private TextView start_time_text_view;
    private TextView end_time_text_view;
    private TextView address_text_view;
    private Bundle bundle;
    private String price;
    private String description;
    private String spot_type;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_listing);
        confirm = findViewById(R.id.confirm_button);

        bundle = getIntent().getExtras();

        price_text_view = findViewById((R.id.listing_price));
        price = bundle.getString("price");
        price_text_view.setText(String.format("%s %s", "Price:", price));

        description_text_view = findViewById(R.id.spot_description);
        description = bundle.getString("description");
        description_text_view.setText(String.format("%s %s", "Description:", description));

        spot_type_text_view = findViewById(R.id.spot_type);
        spot_type = bundle.getString("spot_type");
        spot_type_text_view.setText(String.format("%s %s", "Spot Type:", spot_type));

        start_date_text_view = findViewById(R.id.listing_start_date);
        start_date = bundle.getString("start_date");
        start_date_text_view.setText(String.format("%s %s", "Start Date:", start_date));

        start_time_text_view = findViewById(R.id.listing_start_time);
        start_time = bundle.getString("start_time");
        start_time_text_view.setText(String.format("%s %s", "Start Time:", start_time));

        end_date_text_view = findViewById(R.id.listing_end_date);
        end_date = bundle.getString("end_date");
        end_date_text_view.setText(String.format("%s %s", "Start Time:", end_date));

        end_time_text_view = findViewById(R.id.listing_end_time);
        end_time = bundle.getString("end_time");
        end_time_text_view.setText(String.format("%s %s", "End Time:", end_time));

        address_text_view = findViewById(R.id.Listing_address);
        address = bundle.getString("address");
        address_text_view.setText(String.format("%s %s", "Listing Address:", address));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* test
                System.out.println("price: " + price);
                System.out.println("description: " + description);
                System.out.println("spot type: " + spot_type);
                System.out.println("start date: " + start_date);
                System.out.println("start time: " + start_time);
                System.out.println("end date: " + end_date);
                System.out.println("end time: " + end_time);
                */

                Intent intent = new Intent(ConfirmListingActivity.this, ConfirmationOfCreationActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}