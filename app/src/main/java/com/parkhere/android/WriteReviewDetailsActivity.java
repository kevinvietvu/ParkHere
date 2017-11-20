package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class WriteReviewDetailsActivity extends AppCompatActivity {

    public static WriteReviewDetailsActivity instance = null;
    private Bundle bundle;
    private Button submit;
    private float rating;
    private String reviewText;

    public static boolean ratingBarHasRating(float rating) {
        if (rating > 0) return true;
        else return false;
    }

    public static boolean reviewIsBetween1And140(String reviewText) {
        if (reviewText.length() >= 1 && reviewText.length() <= 140) return true;
        else return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_write_review_details);

        submit = findViewById(R.id.submitButton);

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
                    Intent intent = new Intent(WriteReviewDetailsActivity.this, WriteReviewFinalActivity.class);
                    intent.putExtra("rating", rating);
                    intent.putExtra("review_text", reviewText);
                    startActivity(intent);
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