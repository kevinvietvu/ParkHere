package com.parkhere.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageButton imgButton;
    private DatabaseReference userListingRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ArrayList<String> reviews = new ArrayList<>();
    private FirebaseAuth.AuthStateListener authListener;
    private TextView helloUserText;
    private Bitmap bitmap;
    private ProgressDialog dialog;

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
                profileFullName.setText(firstName + " " + lastName);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_list_item_1, reviews);
                ListView listView = findViewById(R.id.list_view);
                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        imgButton =(ImageButton)findViewById(R.id.account_picture);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null) {
                    Toast.makeText(getApplicationContext(),
                            "Please select image", Toast.LENGTH_SHORT).show();
                } else {
                    dialog = ProgressDialog.show(ProfileActivity.this, "Uploading",
                            "Please wait...", true);
                    //new ImageUploadTask().execute();
                }
            }
        });

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
                    helloUserText.setText(user.getEmail());
                }
            }
        };

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