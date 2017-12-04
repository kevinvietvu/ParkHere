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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrowseListingPaymentActivity extends AppCompatActivity {

    public static BrowseListingPaymentActivity instance = null;
    public String card_number;
    public String cvv;
    public String vehicle_make;
    public String vehicle_model;
    public String vehicle_color;
    public String license_plate_number;
    private Button next_step;
    private Bundle bundle;
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
    private DatabaseReference geoFireRef;
    private DatabaseReference userReservationRef;
    private DatabaseReference userListingRef;
    private DatabaseReference locationRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private int reservationCount;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Map<String, Object> listingData = new HashMap<>();
    private Map<String, Object> locationData = new HashMap<>();

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_browse_listing_payment);
        next_step = findViewById(R.id.next_step);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userReservationRef = database.getReference("Users");
        userListingRef = database.getReference("Users");
        locationRef = database.getReference("Locations");
        geoFireRef = database.getReference("/geoFireListings");

        bundle = getIntent().getExtras();
        final String originalStartDate = bundle.get("original_start_date").toString();
        final String originalEndDate = bundle.get("original_end_date").toString();

        final Listing listing = bundle.getParcelable("listing");

        final String chosenStartDate = listing.getStartDate();
        final String chosenEndDate = listing.getEndDate();

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
                    SplitBookingUtility utility = new SplitBookingUtility();
                    try {
                        if (utility.checkNoSplitDate(originalStartDate, originalEndDate, chosenStartDate, chosenEndDate)) {
                            listingData.put("price", price);
                            listingData.put("spotType", spot_type);
                            listingData.put("startDate", start_date);
                            listingData.put("startTime", start_time);
                            listingData.put("endDate", end_date);
                            listingData.put("endTime", end_time);
                            listingData.put("address", address);
                            listingData.put("userID", creator_id);
                            listingData.put("renterID", user.getUid());
                            listingData.put("userListingPushKey", userListingPushKey);
                            listingData.put("locationPushKey", locationPushKey);
                            listingData.put("vehicleMake", vehicle_make);
                            listingData.put("vehicleModel", vehicle_model);
                            listingData.put("vehicleColor", vehicle_color);
                            listingData.put("licensePlateNumber", license_plate_number);

                            String reservationPushKey = userReservationRef.child(user.getUid()).child("Reservations").child(address).push().getKey();

                            userReservationRef.child(user.getUid()).child("Reservations").child(address).child(reservationPushKey).child("Details").setValue(listingData);
                            userReservationRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    reservationCount = Integer.parseInt(snapshot.child(creator_id).child("ParkingSpots").child(address).child("Details").child("reservationCount").getValue().toString());
                                }

                                @Override
                                public void onCancelled(DatabaseError firebaseError) {
                                    System.out.println("The read failed: " + firebaseError.getMessage());
                                }
                            });
                            userReservationRef.child(creator_id).child("ParkingSpots").child(address).child("Details").child("reservationCount").setValue(reservationCount + 1);

                            locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").child("reservationPushKey").setValue(reservationPushKey);
                            locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").child("renterID").setValue(user.getUid());

                            locationRef.child(address).child("Users").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int userCount = 0;
                                    int listingCount = 0;
                                    for (DataSnapshot d : dataSnapshot.getChildren())
                                        userCount++;
                                    for (DataSnapshot d : dataSnapshot.child(creator_id).child("Renters").getChildren())
                                        listingCount++;

                                    if (userCount < 2 && listingCount < 2) {
                                        System.out.println("TEST GEO FIRE REMOVE");
                                        geoFireRef.child(address).removeValue();
                                    } else {
                                        System.out.println("GEO FIRE TEST NO REMOVE");
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


                        } else if (utility.checkSingleSplitDate(originalStartDate, originalEndDate, chosenStartDate, chosenEndDate)) {
                            ArrayList<Listing> splitListings = utility.singleSplitDate(originalStartDate, originalEndDate, chosenStartDate, chosenEndDate);

                            for (Listing splitListing : splitListings) {
                                if (splitListing.renterID.equals("flag")) {
                                    System.out.println("flag " + splitListing.getStartDate() + " : " + splitListing.getEndDate());
                                    listingData.put("price", price);
                                    listingData.put("spotType", spot_type);
                                    listingData.put("address", address);
                                    listingData.put("userID", creator_id);
                                    listingData.put("startDate", splitListing.getStartDate());
                                    listingData.put("endDate", splitListing.getEndDate());
                                    listingData.put("startTime", start_time);
                                    listingData.put("endTime", end_time);
                                    listingData.put("renterID", user.getUid());
                                    listingData.put("vehicleMake", vehicle_make);
                                    listingData.put("vehicleModel", vehicle_model);
                                    listingData.put("vehicleColor", vehicle_color);
                                    listingData.put("licensePlateNumber", license_plate_number);

                                    locationData.put("userListingPushKey", userListingPushKey);
                                    locationData.put("locationPushKey", locationPushKey);

                                    userListingRef.child(creator_id).child("Listings").child(address).child(userListingPushKey).child("Details").child("startDate").setValue(splitListing.getStartDate());
                                    userListingRef.child(creator_id).child("Listings").child(address).child(userListingPushKey).child("Details").child("endDate").setValue(splitListing.getEndDate());
                                    userListingRef.child(creator_id).child("Listings").child(address).child(userListingPushKey).child("Details").child("renterID").setValue(user.getUid());
                                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").setValue(locationData);

                                    String reservationPushKey = userReservationRef.child(user.getUid()).child("Reservations").child(address).push().getKey();
                                    userReservationRef.child(user.getUid()).child("Reservations").child(address).child(reservationPushKey).child("Details").setValue(listingData);

                                    userReservationRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            reservationCount = Integer.parseInt(snapshot.child(creator_id).child("ParkingSpots").child(address).child("Details").child("reservationCount").getValue().toString());
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError firebaseError) {
                                            System.out.println("The read failed: " + firebaseError.getMessage());
                                        }
                                    });
                                    userReservationRef.child(creator_id).child("ParkingSpots").child(address).child("Details").child("reservationCount").setValue(reservationCount + 1);

                                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").child("reservationPushKey").setValue(reservationPushKey);
                                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").child("renterID").setValue(user.getUid());
                                } else {
                                    Map<String, Object> newListingData = new HashMap<>();
                                    System.out.println("no flag " + splitListing.getStartDate() + " : " + splitListing.getEndDate());
                                    String newLocationPushKey = locationRef.child(address).child("Users").child(user.getUid()).child("Renters").push().getKey();
                                    String newUserListingPushKey = userListingRef.child(user.getUid()).child("Listings").child(address).push().getKey();
                                    newListingData.put("price", price);
                                    newListingData.put("spotType", spot_type);
                                    newListingData.put("address", address);
                                    newListingData.put("userID", creator_id);
                                    newListingData.put("userListingPushKey", newUserListingPushKey);
                                    newListingData.put("locationPushKey", newLocationPushKey);
                                    newListingData.put("startDate", splitListing.getStartDate());
                                    newListingData.put("endDate", splitListing.getEndDate());
                                    newListingData.put("startTime", start_time);
                                    newListingData.put("endTime", end_time);
                                    newListingData.put("renterID", "");

                                    locationData.put("userListingPushKey", newUserListingPushKey);
                                    locationData.put("locationPushKey", newLocationPushKey);
                                    locationData.put("reservationPushKey", "");
                                    locationData.put("renterID", "");

                                    userListingRef.child(creator_id).child("Listings").child(address).child(newUserListingPushKey).child("Details").setValue(newListingData);
                                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(newLocationPushKey).child("Details").setValue(locationData);
                                }

                            }

                        } else if (utility.checkDoubleSplitDate(originalStartDate, originalEndDate, chosenStartDate, chosenEndDate)) {
                            ArrayList<Listing> splitListings = utility.doubleSplitDate(originalStartDate, originalEndDate, chosenStartDate, chosenEndDate);

                            listingData.put("price", price);
                            listingData.put("spotType", spot_type);
                            listingData.put("address", address);
                            listingData.put("userID", creator_id);
                            listingData.put("startTime", start_time);
                            listingData.put("endTime", end_time);
                            listingData.put("userListingPushKey", userListingPushKey);
                            listingData.put("locationPushKey", locationPushKey);
                            for (Listing splitListing : splitListings) {
                                listingData.put("startDate", splitListing.getStartDate());
                                listingData.put("endDate", splitListing.getEndDate());
                                if (splitListing.renterID.equals("flag")) {
                                    System.out.println("flag " + splitListing.getStartDate() + " : " + splitListing.getEndDate());
                                    listingData.put("price", price);
                                    listingData.put("spotType", spot_type);
                                    listingData.put("address", address);
                                    listingData.put("userID", creator_id);
                                    listingData.put("startDate", splitListing.getStartDate());
                                    listingData.put("endDate", splitListing.getEndDate());
                                    listingData.put("startTime", start_time);
                                    listingData.put("endTime", end_time);
                                    listingData.put("renterID", user.getUid());
                                    listingData.put("vehicleMake", vehicle_make);
                                    listingData.put("vehicleModel", vehicle_model);
                                    listingData.put("vehicleColor", vehicle_color);
                                    listingData.put("licensePlateNumber", license_plate_number);

                                    locationData.put("userListingPushKey", userListingPushKey);
                                    locationData.put("locationPushKey", locationPushKey);

                                    userListingRef.child(creator_id).child("Listings").child(address).child(userListingPushKey).child("Details").child("startDate").setValue(splitListing.getStartDate());
                                    userListingRef.child(creator_id).child("Listings").child(address).child(userListingPushKey).child("Details").child("endDate").setValue(splitListing.getEndDate());
                                    userListingRef.child(creator_id).child("Listings").child(address).child(userListingPushKey).child("Details").child("renterID").setValue(user.getUid());
                                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").setValue(locationData);

                                    String reservationPushKey = userReservationRef.child(user.getUid()).child("Reservations").child(address).push().getKey();
                                    userReservationRef.child(user.getUid()).child("Reservations").child(address).child(reservationPushKey).child("Details").setValue(listingData);

                                    userReservationRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            reservationCount = Integer.parseInt(snapshot.child(creator_id).child("ParkingSpots").child(address).child("Details").child("reservationCount").getValue().toString());
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError firebaseError) {
                                            System.out.println("The read failed: " + firebaseError.getMessage());
                                        }
                                    });
                                    userReservationRef.child(creator_id).child("ParkingSpots").child(address).child("Details").child("reservationCount").setValue(reservationCount + 1);

                                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").child("reservationPushKey").setValue(reservationPushKey);
                                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(locationPushKey).child("Details").child("renterID").setValue(user.getUid());
                                } else {
                                    Map<String, Object> newListingData = new HashMap<>();
                                    System.out.println("no flag " + splitListing.getStartDate() + " : " + splitListing.getEndDate());
                                    String newLocationPushKey = locationRef.child(address).child("Users").child(user.getUid()).child("Renters").push().getKey();
                                    String newUserListingPushKey = userListingRef.child(user.getUid()).child("Listings").child(address).push().getKey();
                                    newListingData.put("price", price);
                                    newListingData.put("spotType", spot_type);
                                    newListingData.put("address", address);
                                    newListingData.put("userID", creator_id);
                                    newListingData.put("userListingPushKey", newUserListingPushKey);
                                    newListingData.put("locationPushKey", newLocationPushKey);
                                    newListingData.put("startDate", splitListing.getStartDate());
                                    newListingData.put("endDate", splitListing.getEndDate());
                                    newListingData.put("startTime", start_time);
                                    newListingData.put("endTime", end_time);
                                    newListingData.put("renterID", "");

                                    locationData.put("userListingPushKey", newUserListingPushKey);
                                    locationData.put("locationPushKey", newLocationPushKey);
                                    locationData.put("reservationPushKey", "");
                                    locationData.put("renterID", "");

                                    userListingRef.child(creator_id).child("Listings").child(address).child(newUserListingPushKey).child("Details").setValue(newListingData);
                                    locationRef.child(address).child("Users").child(creator_id).child("Renters").child(newLocationPushKey).child("Details").setValue(locationData);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    if (SplitBookingStartDateActivity.instance != null) {
                        try {
                            SplitBookingStartDateActivity.instance.finish();
                        } catch (Exception e) {
                        }
                    }
                    if (SplitBookingEndDateActivity.instance != null) {
                        try {
                            SplitBookingEndDateActivity.instance.finish();
                        } catch (Exception e) {
                        }
                    }
                    if (BrowseListingPaymentActivity.instance != null) {
                        try {
                            BrowseListingPaymentActivity.instance.finish();
                        } catch (Exception e) {
                        }
                    }
                    Toast.makeText(BrowseListingPaymentActivity.this, "Listing has been reserved!", Toast.LENGTH_LONG).show();
                    Intent main = new Intent(BrowseListingPaymentActivity.this, MainActivity.class);
                    startActivity(main);
                }
                }

        });
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}