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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestFirebaseActivity extends AppCompatActivity {
    private Button  createListingButton;
    private Button  deleteListingButton;
    private TextView helloUserText;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userListingRef;
    private DatabaseReference locationsRef = database.getReference("Locations");

    private DatabaseReference geoFireRef = database.getReference("geoFireListings");
    private GeoFire geoFire = new GeoFire(geoFireRef);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // if user is null launch login activity
                    startActivity(new Intent(TestFirebaseActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    helloUserText.setText("Hello  " + user.getEmail() +"");
                }
            }
        };
        user = auth.getCurrentUser();

        userListingRef = database.getReference("Users");

        userListingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.child("15564 Calgary St, San Leandro, CA 94579, USA").child("Users"));
                //System.out.println(snapshot.child("15564 Calgary St, San Leandro, CA 94579, USA").child("Users").child("Kevin's ID").getKey());

                //System.out.println(snapshot.child("Kevin's ID").child("Listings").child("15564 Calgary St, San Leandro, CA 94579, USA").getKey());
                //System.out.println(snapshot.child("Kevin's ID").child("Listings").child("15564 Calgary St, San Leandro, CA 94579, USA").getValue());

                /**
                 for (DataSnapshot d : snapshot.child("Kevin's ID").child("Listings").child("15564 Calgary St, San Leandro, CA 94579, USA").getChildren()) {
                 Listing post = d.getValue(Listing.class);
                 System.out.println(d.getKey());
                 System.out.println(post.getDescription() + " " + post.getPrice());
                 }
                 */
                /**
                for (DataSnapshot d : snapshot.getChildren()) {
                    System.out.println(d.getKey());
                    System.out.println(d.getValue());
                }*/
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        createListingButton = (Button) findViewById(R.id.create);
        deleteListingButton = (Button) findViewById(R.id.delete);

        helloUserText = (TextView) findViewById(R.id.text_user);

        createListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                Map<String, Object> listingData = new HashMap<String, Object>();

                listingData.put("price", "1");
                listingData.put("description", "boi");
                listingData.put("spotType", "single");
                listingData.put("startDate", "1");
                listingData.put("startTime", "2");
                listingData.put("endDate", "3");
                listingData.put("endTime", "4");
                listingData.put("address" , "15564 Calgary St, San Leandro, CA 94579, USA");

                Map<String, Object> listingData2 = new HashMap<String, Object>();

                listingData2.put("price", "2");
                listingData2.put("description", "aye");
                listingData2.put("spotType", "single");
                listingData2.put("startDate", "2");
                listingData2.put("startTime", "3");
                listingData2.put("endDate", "4");
                listingData2.put("endTime", "5");
                listingData2.put("address" , "15145 Andover St, San Leandro, CA 94579, USA");

                Map<String, Object> listingData3 = new HashMap<String, Object>();

                listingData3.put("price", "3");
                listingData3.put("description", "shidu");
                listingData3.put("spotType", "single");
                listingData3.put("startDate", "4");
                listingData3.put("startTime", "5");
                listingData3.put("endDate", "6");
                listingData3.put("endTime", "7");
                listingData3.put("address" , "16775 Ventry Way, San Lorenzo, CA 94580, USA");

                locationsRef.child("15564 Calgary St, San Leandro, CA 94579, USA").child("Users").child("Kevin's ID").setValue(true);
                locationsRef.child("15564 Calgary St, San Leandro, CA 94579, USA").child("Users").child("Stanley's ID").setValue(true);
                locationsRef.child("15145 Andover St, San Leandro, CA 94579, USA").child("Users").child("Ricky's ID").setValue(true);
                locationsRef.child("16775 Ventry Way, San Lorenzo, CA 94580, USA").child("Users").child("Nelson's ID").setValue(true);
                userListingRef.child("Kevin's ID").child("Listings").child("15564 Calgary St, San Leandro, CA 94579, USA").child("Details").setValue(listingData);
                userListingRef.child("Nelson's ID").child("Listings").child("16775 Ventry Way, San Lorenzo, CA 94580, USA").child("Details").setValue(listingData2);
                userListingRef.child("Ricky's ID").child("Listings").child("15145 Andover St, San Leandro, CA 94579, USA").child("Details").setValue(listingData3); */

                Map<String, Object> listingData = new HashMap<String, Object>();

                listingData.put("price", "1");
                listingData.put("description", "boi");
                listingData.put("spotType", "single");
                listingData.put("startDate", "1");
                listingData.put("startTime", "2");
                listingData.put("endDate", "3");
                listingData.put("endTime", "4");
                listingData.put("address" , "15564 Calgary St, San Leandro, CA 94579, USA");

                userListingRef.child("Kevin's ID").child("Listings").child("15564 Calgary St, San Leandro, CA 94579, USA").child("Details").setValue(listingData);

            }
        });

        deleteListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 userListingRef.child("Kevin's ID").child("Listings").child("15564 Calgary St, San Leandro, CA 94579, USA").child("Details").setValue(null);
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
        return addresses.get(0);
    }
    //might have to use LatLong object instead
    public static Address getAddressFromGeoLocation(GeoLocation LatLong, Context context) throws IOException {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = geocoder.getFromLocation(LatLong.latitude, LatLong.longitude, 1);
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
