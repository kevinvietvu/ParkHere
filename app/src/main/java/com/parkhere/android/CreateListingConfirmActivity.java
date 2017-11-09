package com.parkhere.android;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateListingConfirmActivity extends AppCompatActivity {

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
    private Double price;
    private String description;
    private String spot_type;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String address;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userListingRef;
    private DatabaseReference locationsRef;
    private DatabaseReference geoFireRef;
    private GeoFire geoFire;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Map<String, Object> listingData = new HashMap<String, Object>();

    public static CreateListingConfirmActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_create_listing_confirm);
        confirm = findViewById(R.id.confirm_button);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        userListingRef = database.getReference("Users");
        locationsRef = database.getReference("Locations");
        geoFireRef = database.getReference("geoFireListings");
        geoFire = new GeoFire(geoFireRef);

        bundle = getIntent().getExtras();

        price_text_view = findViewById((R.id.listing_price));
        price = bundle.getDouble("price");
        price_text_view.setText(String.format("%s %s", "Price:", price));

        listingData.put("price", price);

        description_text_view = findViewById(R.id.spot_description);
        description = bundle.getString("description");
        description_text_view.setText(String.format("%s %s", "Description:", description));

        listingData.put("description" , description );

        spot_type_text_view = findViewById(R.id.spot_type);
        spot_type = bundle.getString("spot_type");
        spot_type_text_view.setText(String.format("%s %s", "Spot Type:", spot_type));

        listingData.put("spotType" , spot_type );

        start_date_text_view = findViewById(R.id.listing_start_date);
        start_date = bundle.getString("start_date");
        start_date_text_view.setText(String.format("%s %s", "Start Date:", start_date));

        listingData.put("startDate", start_date );

        start_time_text_view = findViewById(R.id.listing_start_time);
        String t1 = bundle.getString("start_time");
        int h1 = Integer.parseInt(t1.substring(0, 2));
        int m1 = Integer.parseInt(t1.substring(3, 5));
        String meridiem1;
        if (h1 < 12) meridiem1 = "AM";
        else meridiem1 = "PM";
        h1 = h1 % 12;
        if (h1 == 0) { h1 = 12; }
        start_time = h1 + ":" + m1 + " " + meridiem1;
        start_time_text_view.setText(String.format("%s %s", "Start Time:", start_time));

        listingData.put("startTime", start_time );

        end_date_text_view = findViewById(R.id.listing_end_date);
        end_date = bundle.getString("end_date");
        end_date_text_view.setText(String.format("%s %s", "End Date:", end_date));

        listingData.put("endDate", end_date );

        end_time_text_view = findViewById(R.id.listing_end_time);
        String t2 = bundle.getString("end_time");
        int h2 = Integer.parseInt(t2.substring(0, 2));
        int m2 = Integer.parseInt(t2.substring(3, 5));
        String meridiem2;
        if (h2 < 12) meridiem2 = "AM";
        else meridiem2 = "PM";
        h2 = h2 % 12;
        if (h2 == 0) { h2 = 12; }
        end_time = h2 + ":" + m2 + " " + meridiem2;
        end_time_text_view.setText(String.format("%s %s", "End Time:", end_time));


        listingData.put("endTime" , end_time);

        address_text_view = findViewById(R.id.Listing_address);
        address = bundle.getString("address");
        address_text_view.setText(String.format("%s %s", "Listing Address:", address));

        listingData.put("address" , address);

        listingData.put("userID", user.getUid());

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

                /**
                 locationsRef.child("16775 Ventry Way, San Lorenzo, CA 94580, USA").child("Users").child("Nelson's ID").setValue(true);
                 userListingRef.child("Kevin's ID").child("Listings").child("15564 Calgary St, San Leandro, CA 94579, USA").child("Details").setValue(listingData);
                 */

                try {

                    Address addressToInsertInFirebase = ProfileActivity.getGeoLocationFromAddress(address, CreateListingConfirmActivity.this);
                    geoFire.setLocation(address, new GeoLocation(addressToInsertInFirebase.getLatitude(),addressToInsertInFirebase.getLongitude()));
                    //setValue to user's name later
                    locationsRef.child(address).child("Users").child(user.getUid()).setValue("");
                    userListingRef.child(user.getUid()).child("Listings").child(address).child("Details").setValue(listingData);
                }
                catch (Exception e) {
                    Log.e("IOException", e.getMessage());
                }

                Intent intent = new Intent(CreateListingConfirmActivity.this, CreateListingFinalActivity.class);
                intent.putExtras(bundle);
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