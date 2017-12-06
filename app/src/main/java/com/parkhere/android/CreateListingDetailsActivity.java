package com.parkhere.android;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateListingDetailsActivity extends AppCompatActivity {

    private Button confirm;
    private TextView description_text_view;
    private TextView spot_type_text_view;
    private TextView start_date_text_view;
    private TextView end_date_text_view;
    private TextView start_time_text_view;
    private TextView end_time_text_view;
    private TextView address_text_view;
    private EditText price_input;
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
    private Map<String, Object> locationData = new HashMap<>();

    public static boolean priceIsNotNull(Object o) {
        if (o != null) return true;
        else return false;
    }

    public static boolean priceMustBeBetween1And999(Double price) {
        if (price >= 1.0 && price <= 999.0) return true;
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing_details);
        confirm = findViewById(R.id.confirm_button);
        price_input = findViewById(R.id.enter_price);

        bundle = getIntent().getExtras();

        address_text_view = findViewById(R.id.Listing_address);
        address = bundle.getString("address");
        address_text_view.setText(String.format("%s %s", "Listing Address:", address));

        description_text_view = findViewById(R.id.spot_description);
        description = bundle.getString("description");
        description_text_view.setText(String.format("%s %s", "Description:", description));

        spot_type_text_view = findViewById(R.id.spot_type);
        spot_type = bundle.getString("spotType");
        spot_type_text_view.setText(String.format("%s %s", "Spot Type:", spot_type));

        start_date_text_view = findViewById(R.id.listing_start_date);
        start_date = bundle.getString("start_date");
        start_date_text_view.setText(String.format("%s %s", "Start Date:", start_date));

        start_time_text_view = findViewById(R.id.listing_start_time);
        String t1 = bundle.getString("start_time");
        int h1 = Integer.parseInt(t1.substring(0, 2));
        int m1 = Integer.parseInt(t1.substring(3, 5));
        String startMeridiem;
        if (h1 < 12) startMeridiem = "AM";
        else startMeridiem = "PM";
        h1 = h1 % 12;
        if (h1 == 0) { h1 = 12; }

        String startHour;
        String startMinute;
        if (h1 < 10) {
            startHour = "0" + h1;
        } else {
            startHour = "" + h1;
        }
        startMinute = "00";

        start_time = startHour + ":" + startMinute + " " + startMeridiem;
        start_time_text_view.setText(String.format("%s %s", "Start Time:", start_time));

        end_date_text_view = findViewById(R.id.listing_end_date);
        end_date = bundle.getString("end_date");
        end_date_text_view.setText(String.format("%s %s", "End Date:", end_date));

        end_time_text_view = findViewById(R.id.listing_end_time);
        String t2 = bundle.getString("end_time");
        int h2 = Integer.parseInt(t2.substring(0, 2));
        int m2 = Integer.parseInt(t2.substring(3, 5));
        String endMeridiem;
        if (h2 < 12) endMeridiem = "AM";
        else endMeridiem = "PM";
        h2 = h2 % 12;
        if (h2 == 0) { h2 = 12; }

        String endHour;
        String endMinute;
        if (h2 < 10) {
            endHour = "0" + h2;
        } else {
            endHour = "" + h2;
        }
        endMinute = "00";

        end_time = endHour + ":" + endMinute + " " + endMeridiem;
        end_time_text_view.setText(String.format("%s %s", "End Time:", end_time));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!price_input.getText().toString().isEmpty())
                    price = Double.parseDouble(price_input.getText().toString());
                if (!startTimeAndEndTimeMustNotBeSame(Integer.parseInt(start_time.substring(0,2)) ,(Integer.parseInt(end_time.substring(0,2)))))
                    Toast.makeText(CreateListingDetailsActivity.this, "Start and end hour cannot be the same", Toast.LENGTH_LONG).show();
                if (!priceIsNotNull(price)) {
                    Toast.makeText(CreateListingDetailsActivity.this, "Please enter a value for price", Toast.LENGTH_LONG).show();
                } else if (!priceMustBeBetween1And999(price)) {
                    Toast.makeText(CreateListingDetailsActivity.this, "Please enter a price between 1 and 999", Toast.LENGTH_LONG).show();
                } else {

                    try {
                        auth = FirebaseAuth.getInstance();
                        user = auth.getCurrentUser();

                        userListingRef = database.getReference("Users");
                        locationsRef = database.getReference("Locations");

                        String locationPushKey = locationsRef.child(address).child("Users").child(user.getUid()).child("Renters").push().getKey();
                        String userListingPushKey = userListingRef.child(user.getUid()).child("Listings").child(address).push().getKey();


                        geoFireRef = database.getReference("geoFireListings");
                        geoFire = new GeoFire(geoFireRef);

                        listingData.put("address", address);
                        listingData.put("description", description);
                        listingData.put("endDate", end_date);
                        listingData.put("endTime", end_time);
                        listingData.put("price", price);
                        listingData.put("spotType", spot_type);
                        listingData.put("startDate", start_date);
                        listingData.put("startTime", start_time);
                        listingData.put("userID", user.getUid());
                        listingData.put("locationPushKey", locationPushKey);
                        listingData.put("userListingPushKey", userListingPushKey);

                        locationData.put("userListingPushKey", userListingPushKey);
                        locationData.put("locationPushKey", locationPushKey);
                        locationData.put("reservationPushKey", "");
                        locationData.put("renterID", "");

                        userListingRef.child(user.getUid()).child("Listings").child(address).child(userListingPushKey).child("Details").setValue(listingData);
                        locationsRef.child(address).child("Users").child(user.getUid()).child("Renters").child(locationPushKey).child("Details").setValue(locationData);

                        Address addressToInsertInFirebase = ManageListingsActivity.getGeoLocationFromAddress(address, CreateListingDetailsActivity.this);
                        geoFire.setLocation(address, new GeoLocation(addressToInsertInFirebase.getLatitude(),addressToInsertInFirebase.getLongitude()));
                    }
                    catch (Exception e) {
                        Log.e("IOException", e.getMessage());
                    }


                    if(CreateListingChooseSpotActivity.instance != null) {
                        try {
                            CreateListingChooseSpotActivity.instance.finish();
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

                    Toast.makeText(CreateListingDetailsActivity.this, "Listing has been created!", Toast.LENGTH_LONG).show();
                    Intent map = new Intent(CreateListingDetailsActivity.this, MainActivity.class);
                    startActivity(map);
                    finish();
                }
            }
        });
    }

    public static boolean startTimeAndEndTimeMustNotBeSame(int startHour, int endHour) {
        if (startHour != endHour) return true;
        return false;
    }
}