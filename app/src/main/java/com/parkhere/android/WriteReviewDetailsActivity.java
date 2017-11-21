package com.parkhere.android;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class WriteReviewDetailsActivity extends AppCompatActivity {

    private Bundle bundle;
    private Button submit;
    private float rating;
    private String reviewText;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;
    private DatabaseReference locationsRef;

    public static WriteReviewDetailsActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_write_review_details);

        submit = findViewById(R.id.submitButton);

        final Bundle listingInfo = getIntent().getExtras();

        userRef = database.getReference("Users");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = ((RatingBar) findViewById(R.id.ratingBar)).getRating();
                reviewText = ((EditText) findViewById(R.id.reviewText)).getText().toString();
                if (!ratingBarHasRating(rating)) {
                    Toast.makeText(WriteReviewDetailsActivity.this, "Please enter a rating", Toast.LENGTH_LONG).show();
                } else if (!reviewIsBetween1And140(reviewText)) {
                    Toast.makeText(WriteReviewDetailsActivity.this, "Please write a review between 1 and 140 characters", Toast.LENGTH_LONG).show();
                } else {

                    //String address = (String) listingInfo.get("address");
                    String userID = (String) listingInfo.get("userID");

                    Map<String,Object> reviewDetails = new HashMap<>();

                    reviewDetails.put("rating", rating);
                    reviewDetails.put("review", reviewText);

                    userRef.child(userID).child("Reviews").push().child("Details").setValue(reviewDetails);

                    Toast.makeText(WriteReviewDetailsActivity.this, "Review has been submitted!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

    public static boolean ratingBarHasRating(float rating) {
        if (rating > 0) return true;
        else return false;
    }

    public static boolean reviewIsBetween1And140(String reviewText) {
        if (reviewText.length() >= 1 && reviewText.length() <= 140) return true;
        else return false;
    }
}