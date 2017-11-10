package com.parkhere.android;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    private Button createListingButton;
    private Button myListingsButton;
    private Button myReservationsButton;
    private TextView helloUserText;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("user");

    private DatabaseReference geoFireRef = database.getReference("user/listings");
    private GeoFire geoFire = new GeoFire(geoFireRef);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        createListingButton = (Button) findViewById(R.id.create_listing);
        myListingsButton = findViewById(R.id.my_listings);
        myReservationsButton = findViewById(R.id.my_reservations);

        helloUserText = (TextView) findViewById(R.id.text_user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // if user is null launch login activity
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    helloUserText.setText("Hello  " + user.getEmail() +"");
                }
            }
        };

        createListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(ProfileActivity.this, CreateListingMapsActivity.class);
                //mapIntent.setPackage("com.google.android.apps.maps");
                //if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
                //}
            }
        });

        myListingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ViewUserListingsActivity.class);
                startActivity(intent);
            }
        });

        myReservationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ViewUserReservationsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * User Activity
     */

    //https://stackoverflow.com/questions/9698328/how-to-get-coordinates-of-an-address-in-android
    public static Address getGeoLocationFromAddress(String address, Context context) throws IOException {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = geocoder.getFromLocationName(address, 1);
        if (addresses == null || addresses.get(0) == null) {
            Address nullAddress = new Address(Locale.getDefault());
            nullAddress.setLatitude(0);
            nullAddress.setLongitude(0);
            return nullAddress;
        }
        else
        return addresses.get(0);
    }

    public static Address getAddressFromGeoLocation(GeoLocation LatLong, Context context) throws IOException {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = geocoder.getFromLocation(LatLong.latitude, LatLong.longitude, 1);
        if (addresses == null || addresses.get(0) == null) {
            Address nullAddress = new Address(Locale.getDefault());
            nullAddress.setAddressLine(0,"");
            return nullAddress;
        }
        else
        return addresses.get(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}
