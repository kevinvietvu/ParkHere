package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BrowseListingConfirmActivity extends AppCompatActivity {

    private Button confirm;
    private TextView listing_address_text_view;
    private TextView card_number_text_view;
    private TextView cvv_text_view;
    private Bundle bundle;
    private String listing_address;
    private String card_number;
    private String cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_listing_confirm);
        confirm = findViewById(R.id.confirm_button);

        bundle = getIntent().getExtras();

        listing_address_text_view = findViewById(R.id.listing_address);
        listing_address = bundle.getString("listing_address");
        listing_address_text_view.setText(String.format("%s %s", "Listing address:", listing_address));

        card_number_text_view = findViewById(R.id.card_number);
        card_number = bundle.getString("card_number");
        card_number_text_view.setText(String.format("%s %s", "Listing address:", card_number));

        cvv_text_view = findViewById(R.id.cvv);
        cvv = bundle.getString("cvv");
        cvv_text_view.setText(String.format("%s %s", "Listing address:", cvv));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseListingConfirmActivity.this, BrowseListingFinalActivity.class);
                startActivity(intent);
            }
        });
    }

}