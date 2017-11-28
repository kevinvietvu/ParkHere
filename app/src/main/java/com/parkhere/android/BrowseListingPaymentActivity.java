package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class BrowseListingPaymentActivity extends AppCompatActivity {

    private Button next_step;
    private Bundle bundle;
    public String card_number;
    public String cvv;
    private Double price;
    private String spot_type;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String address;
    private String creator_id;
    private String userListingPushKey;
    private String locationPushKey;
    public String vehicle_make;
    public String vehicle_model;
    public String vehicle_color;
    public String license_plate_number;

    private DatabaseReference geoFireRef;
    private DatabaseReference userReservationRef;
    private DatabaseReference locationRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Map<String, Object> listingData = new HashMap<>();

    public static BrowseListingPaymentActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_browse_listing_payment);
        next_step = findViewById(R.id.next_step);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userReservationRef = database.getReference("Users");
        locationRef = database.getReference("Locations");
        geoFireRef = database.getReference("/geoFireListings");

        Listing listing = getIntent().getExtras().getParcelable("listing");

        address = listing.getAddress();

        price = listing.getPrice();

        spot_type = listing.getSpotType();

        start_date = listing.getStartDate();

        start_time = listing.getStartTime();

        end_date = listing.getEndDate();

        end_time = listing.getEndTime();

        creator_id = listing.getUserID();

        userListingPushKey = listing.getUserListingPushKey();

        locationPushKey = listing.getLocationPushKey();

        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_number = ((EditText) findViewById(R.id.enter_card_number)).getText().toString();
                cvv = ((EditText) findViewById(R.id.enter_cvv)).getText().toString();
                vehicle_make = ((EditText) findViewById(R.id.enter_vehicle_make)).getText().toString();
                vehicle_model = ((EditText) findViewById(R.id.enter_vehicle_model)).getText().toString();
                vehicle_color = ((EditText) findViewById(R.id.enter_vehicle_color)).getText().toString();
                license_plate_number = ((EditText) findViewById(R.id.enter_license_plate_number)).getText().toString();

                if (!checkCardLengthBetween12And19(card_number)) {
                    Toast.makeText(BrowseListingPaymentActivity.this, "Card Length must be between 12 and 19 digits",
                            Toast.LENGTH_LONG).show();
                }
                else if (!checkCVVLengthIs3(cvv)) {
                    Toast.makeText(BrowseListingPaymentActivity.this, "CVV must be 3 digits",
                            Toast.LENGTH_LONG).show();
                }
                else if (!vehicleInfoIsNotNull(vehicle_make)
                        || !vehicleInfoIsNotNull(vehicle_model)
                        || !vehicleInfoIsNotNull(vehicle_color)) {
                    Toast.makeText(BrowseListingPaymentActivity.this, "Please enter vehicle information",
                            Toast.LENGTH_LONG).show();
                }
                else if (!checkIfLicensePlateNumberIsBetween1And7(license_plate_number)) {
                    Toast.makeText(BrowseListingPaymentActivity.this, "Please enter a valid license plate number",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    listingData.put("price", price);

                    listingData.put("spotType" , spot_type );

                    listingData.put("startDate", start_date );

                    listingData.put("startTime", start_time );

                    listingData.put("endDate", end_date );

                    listingData.put("endTime" , end_time);

                    listingData.put("address" , address);

                    listingData.put("userID", creator_id);

                    listingData.put("renterID", user.getUid());

                    listingData.put("userListingPushKey", userListingPushKey);

                    listingData.put("locationPushKey", locationPushKey);

                    listingData.put("vehicleMake", vehicle_make);

                    listingData.put("vehicleModel", vehicle_model);

                    listingData.put("vehicleColor", vehicle_color);

                    listingData.put("licensePlateNumber", license_plate_number);

                    String reservationPushKey =  userReservationRef.child(user.getUid()).child("Reservations").child(address).push().getKey();

                    userReservationRef.child(user.getUid()).child("Reservations").child(address).child(reservationPushKey).child("Details").setValue(listingData);

                    //might need to change database structure later
                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").child("reservationPushKey").setValue(reservationPushKey);
                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").child("renterID").setValue(user.getUid());

                    geoFireRef.child(address).removeValue();

                    if(BrowseListingPaymentActivity.instance != null) {
                        try {
                            BrowseListingPaymentActivity.instance.finish();
                        } catch (Exception e) {}
                    }
                    Toast.makeText(BrowseListingPaymentActivity.this, "Listing has been reserved!", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        });
    }

    public static boolean checkCardLengthBetween12And19(String card_number) {
        if (card_number.length() >= 12 && card_number.length() <= 19)
            return true;
        else
            return false;
    }

    public static boolean checkCVVLengthIs3(String cvv) {
        if (cvv.length() == 3)
            return true;
        else
            return false;
    }

    public static boolean vehicleInfoIsNotNull(Object o) {
        if (o != null) return true;
        else return false;
    }

    public static boolean checkIfLicensePlateNumberIsBetween1And7(String license_plate_number) {
        if (license_plate_number.length() >= 1 && license_plate_number.length() <= 7) return true;
        else return false;
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}