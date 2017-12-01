package com.parkhere.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView profileFullName;
    private DatabaseReference userListingRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ArrayList<String> reviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        userListingRef = database.getReference("Users");

        final TextView profileFullName = findViewById(R.id.profile_heading);

        final Bundle selectedListing = getIntent().getExtras();


        userListingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String userID;
                if (selectedListing == null) {
                    userID = user.getUid();
                }
                else {
                    userID = selectedListing.get("userID").toString();
                }
                for (DataSnapshot d : snapshot.child(userID).child("ParkingSpots").getChildren()) {
                    final String address = d.getKey();
                    for (DataSnapshot s : snapshot.child(userID).child("ParkingSpots").child(address).child("Reviews").getChildren()) {
                        String review = s.child("Details").child("review").getValue().toString();
                        String rating = s.child("Details").child("rating").getValue().toString();
                        String reviewDetails = "Parking Spot : " + address + "\n Description : " + review  + "\n Rating : " + rating;
                        reviews.add(reviewDetails);
                    }
                }
                String firstName = (String) snapshot.child(userID).child("Profile").child("firstName").getValue();
                String lastName = (String) snapshot.child(userID).child("Profile").child("lastName").getValue();
                profileFullName.setText(firstName + " " + lastName + "'s Profile");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_list_item_1, reviews);
                ListView listView = findViewById(R.id.list_view);
                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
}