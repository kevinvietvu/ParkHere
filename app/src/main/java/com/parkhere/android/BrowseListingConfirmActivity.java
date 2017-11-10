package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BrowseListingConfirmActivity extends AppCompatActivity {

    private Button confirm;
    private TextView listing_address_text_view;
    private TextView card_number_text_view;
    private TextView cvv_text_view;
    private Bundle bundle;
    private String price;
    private String description;
    private String spot_type;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String address;
    private String creator_id;
    private String card_number;
    private String cvv;

    private DatabaseReference geoFireRef;
    private DatabaseReference userReservationRef;
    private DatabaseReference locationRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Map<String, Object> listingData = new HashMap<>();

    public static BrowseListingConfirmActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_browse_listing_confirm);
        confirm = findViewById(R.id.confirm_button);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userReservationRef = database.getReference("Users");
        locationRef = database.getReference("Locations");
        geoFireRef = database.getReference("/geoFireListings");

        bundle = getIntent().getExtras();

        listing_address_text_view = findViewById(R.id.listing_address);
        address = bundle.getString("address");
        listing_address_text_view.setText(String.format("%s %s", "Listing address:", address));

        price = bundle.getString("price");

        description = bundle.getString("description");

        spot_type = bundle.getString("spot_type");

        start_date = bundle.getString("start_date");

        start_time = bundle.getString("start_time");

        end_date = bundle.getString("end_date");

        end_time = bundle.getString("end_time");

        creator_id = bundle.getString("creator_id");

        listingData.put("price", price);

        listingData.put("description" , description );

        listingData.put("spotType" , spot_type );

        listingData.put("startDate", start_date );

        listingData.put("startTime", start_time );

        listingData.put("endDate", end_date );

        listingData.put("endTime" , end_time);

        listingData.put("address" , address);

        listingData.put("userID", creator_id);


        card_number_text_view = findViewById(R.id.card_number);
        card_number = bundle.getString("card_number");
        card_number_text_view.setText(String.format("%s %s", "Card Number:", card_number));

        cvv_text_view = findViewById(R.id.cvv);
        cvv = bundle.getString("cvv");
        cvv_text_view.setText(String.format("%s %s", "CVV:", cvv));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userReservationRef.child(user.getUid()).child("Reservations").child(address).child("Details").setValue(listingData);

                //might need to change database structure later
                locationRef.child(address).child("Users").child(creator_id).setValue(user.getUid());

                geoFireRef.child(address).removeValue();

                Intent intent = new Intent(BrowseListingConfirmActivity.this, BrowseListingFinalActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}
